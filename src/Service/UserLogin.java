package Service;

import DataAccess.AuthorizationDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import Request.LoginRequest;
import Response.UserLoginResponse;

import java.sql.Connection;

/**
 * Makes requests and responses for userLogin
 */
public class UserLogin {

    private UserLoginResponse response;
    private LoginRequest request;
    Database data;

    public UserLogin(LoginRequest userReq){
        this.request = userReq;
        this.data = new Database();

    }

    public UserLoginResponse loginUser() throws DataAccessException {
        Connection connection = data.openConnection();
        try {
            UserDao user = new UserDao(connection);
            if (user.searchUsername(request.getUsername())){
                if(user.searchAccount(request.getUsername(), request.getPassword())){
                    try {
                        User loggedUser = user.findUser(request.getUsername());
                        AuthorizationDao auth = new AuthorizationDao(connection);
                        response = new UserLoginResponse(auth.findAuth(request.getUsername()).getAuthToken(), loggedUser.getUsername(), loggedUser.getPersonID(), true);
                        data.closeConnection(true);
                        return new UserLoginResponse(response.getAuthtoken(), request.getUsername(), loggedUser.getPersonID(), true);

                    } catch (DataAccessException ex){
                        ex.printStackTrace();
                        data.closeConnection(false);
                        return new UserLoginResponse("Error: Could not match the AuthToken and Username", false);
                    }


                }
                else {
                    data.closeConnection(false);
                    return new UserLoginResponse("Error:The username and password do not match", false);
                }


            }
            else {
                data.closeConnection(false);
                return new UserLoginResponse("Error:This username does not exist", false);
            }


        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        data.closeConnection(false);
        return new UserLoginResponse("Internal server error, could not log user in", false);

    }
}
