package app.roque.com.loginorm.repository;

import com.orm.SugarRecord;

import java.util.List;

import app.roque.com.loginorm.model.User;

/**
 * Created by keving on 16/10/2017.
 */

public class UserRepository {

    public static List<User> list(){
        List<User> users = SugarRecord.listAll(User.class);
        return users;
    }

    public static User read(Long id){
        User user = SugarRecord.findById(User.class, id);
        return user;
    }

    public static User login(String username, String password){
        List<User> users = SugarRecord.listAll(User.class);
        for (User user : users){
            if(user.getEmail().equalsIgnoreCase(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public static void create(String fullname, String email, String password){
        User user = new User(fullname, email, password);
        SugarRecord.save(user);
    }

    public static void update(String fullname, String email, String password, Long id){
        User user = SugarRecord.findById(User.class, id);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(password);
        SugarRecord.save(user);
    }

    public static void delete(Long id){
        User user = SugarRecord.findById(User.class, id);
        SugarRecord.delete(user);
    }

    public static User getUser(String username){
        List<User> users = SugarRecord.listAll(User.class);
        for (User user : users){
            if(user.getEmail().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }
}
