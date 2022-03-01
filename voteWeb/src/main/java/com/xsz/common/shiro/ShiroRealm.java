package java.com.xsz.common.shiro;

import com.xsz.system.domain.Menu;
import com.xsz.system.domain.Role;
import com.xsz.system.domain.User;
import com.xsz.system.service.MenuService;
import com.xsz.system.service.RoleService;
import com.xsz.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    /**
     * 授权模块，获取用户角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取用户角色集
        List<Role> roleList = this.roleService.findUserRole(username);
        Set<String> roleSet =  roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(roleSet);
        //获取用户权限集
        List<Menu> permissionList = this.menuService.findUserPermissions(username);
        Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户输入的用户名和密码
        String userName=(String)token.getPrincipal();
        String password =new String((char[])token.getCredentials());
        //通过用户名到数据库查询用户信息
        User user = this.userService.findByName(userName);
        if(user == null){
            throw new UnknownAccountException("用户名或密码错误");
        }
        if(!password.equals(user.getPassword())){
            throw new IncorrectCredentialsException("用户名或密码错误");
        }
        if(User.STATUS_LOCK.equals(user.getStatus())){
            throw  new LockedAccountException("账户被锁定，请联系管理员");
        }
        return new SimpleAuthenticationInfo(user,password,getName());
    }

    /**
     * 清除权限缓存
     * 使用方法：在需要清除哟欧股权限的地方注入ShiroRealm
     * 然后调用其ClearCache方法
     */
    public void clearCache(){
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
