
package com.lambdaschool.javasafezoos;

import com.lambdaschool.javasafezoos.models.Role;
import com.lambdaschool.javasafezoos.models.User;
import com.lambdaschool.javasafezoos.models.UserRoles;
import com.lambdaschool.javasafezoos.repositories.RoleRepository;
import com.lambdaschool.javasafezoos.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    RoleRepository rolerepos;
    UserRepository userrepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos)
    {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
    }

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("zoodata");
        Role r3 = new Role("animaldata");
        Role r4 = new Role("mgr");

        ArrayList<UserRoles> roleAdminList = new ArrayList<>();
        roleAdminList.add(new UserRoles(new User(), r1));

        ArrayList<UserRoles> roleZooDataList = new ArrayList<>();
        roleZooDataList.add(new UserRoles(new User(), r2));

        ArrayList<UserRoles> roleAnimalDataList = new ArrayList<>();
        roleAnimalDataList.add(new UserRoles(new User(), r3));

        ArrayList<UserRoles> roleMgrList = new ArrayList<>();
        roleMgrList.add(new UserRoles(new User(), r4));

        rolerepos.save(r1);
        rolerepos.save(r2);
        rolerepos.save(r3);
        rolerepos.save(r4);

        User u1 = new User("useradmin", "1234", roleAdminList);
        User u2 = new User("userzoodata", "2345", roleZooDataList);
        User u3 = new User("useranimaldata", "3456", roleAnimalDataList);
        User u4 = new User("usermgr", "4567", roleMgrList);

        userrepos.save(u1);
        userrepos.save(u2);
        userrepos.save(u3);
        userrepos.save(u4);
    }
}
