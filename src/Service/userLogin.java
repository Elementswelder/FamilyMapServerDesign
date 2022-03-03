package Service;

import DataAccess.AuthorizationDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import Request.userLoginRequest;
import Response.userLoginResponse;

import javax.xml.crypto.Data;

/**
 * Makes requests and responses for userLogin
 */
public class userLogin {

    private userLoginResponse response;
    private userLoginRequest request;

    public userLogin(userLoginRequest userReq){
        this.request = userReq;
        boolean succcess = false;
        Database data = new Database();
        try {
            UserDao user = new UserDao(data.openConnection());
            if (user.searchUsername(request.getUsername())){
                if(user.searchAccount(userReq.getUsername(), userReq.getPassword())){
                    try {
                        User loggedUser = user.findUser(request.getUsername());
                        AuthorizationDao auth = new AuthorizationDao(data.getConnection());
                        response = new userLoginResponse(auth.findAuth(request.getUsername()).getAuthToken(), loggedUser.getUsername(), loggedUser.getPersonID(), true);
                        succcess = true;
                    } catch (DataAccessException ex){
                        ex.printStackTrace();
                        throw new DataAccessException("Could not match the AuthToken and Username");
                    }


                }
                else {
                    throw new DataAccessException("Incorrect password!");
                }


            }
            else {
                throw new DataAccessException("This username does NOT exsist!");
            }


        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        if(!succcess){
            response = new userLoginResponse("Could not log the user in!", false);
        }

    }

    public userLoginResponse getResponse(){
      return response;
    }

    /**
     * Log the user into the application
     * @param username the username to login
     * @return if it was successful or not
     */
    public boolean loginUser(String username){

        return false;
    }

    /**
     * Get the authtoken associaed with the user to match
     * @param username to check if the auth token matches
     * @return the authtoken
     */
    public String getAuthToken(String username){

        return null;
    }

}
