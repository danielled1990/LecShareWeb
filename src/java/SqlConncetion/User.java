/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SqlConncetion;

import Exception.DBException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Danielle
 */
public class User extends SqlCon {

    private static final int CARDS_IN_DECK = 15;
    
    private int id;
    private String username;


    
    private User(){
        
    }
    
    public static User validateCredentials(String email, String password) throws SQLException, DBException {
       Integer validatedUserID = null;
        User user = null;
        //We'll use a query to get the infromation we want to initialize a user with
        StringBuilder query = new StringBuilder();

        query.append("SELECT name,email, password, user_id ");
        query.append("FROM userinfo ");
        query.append(String.format("WHERE email = '%s';", email));

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query.toString());

            if(resultSet.next()){      
                //If the credentials are correct, we'll return the id of the current user
                if(resultSet.getString("email").equals(email) &&
                        resultSet.getString("password").equals(password)){
                    user = new User();
                    user.id = resultSet.getInt("user_id");
                    user.username = resultSet.getString("name");
                }
                else{
                    throw new DBException("Invalid password entered");
                }
            }
            else{
                throw new DBException("No User of the given name exists");
            }

            statement.close();
        }
        
        return user;
    }
    
    public static User getUser(int userID) throws SQLException, DBException{
        User user = null;
        
        //We'll use a query to get the infromation we want to initialize a user with
        StringBuilder query = new StringBuilder();

        query.append("SELECT user_id, name");
        query.append(" FROM userinfo ");
        //query.append("JOIN NeosSQL.UserInfo on NeosSQL.Users.id = NeosSQL.UserInfo.id ");
        query.append(String.format(" WHERE user_id = %d;", userID));

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query.toString());

            if(resultSet.next()){                
                //Each singular result we iterate through is a row in the table
                user = new User();
                user.id = resultSet.getInt("user_id");
                user.username = resultSet.getString("name");
//                user.gold = resultSet.getInt("gold");
            }
            else{
                throw new DBException("No User of the given ID exists");
            }

            statement.close();
        }
        
        return user;
    }
    
    public static User createUser(String username, String password,String email,String school)
        throws SQLException, DBException{
        dbConnection.setAutoCommit(false);
        
        User createdUser = null;
        StringBuilder query;
        
       try{
         //   Statement st = dbConnection.createStatement();
          //  String sql = "insert into LecShare.login"+" (username,password)"+" values("+username+","
          //          +password+")";
       //   String sql = "insert into login (username,password) values(susu,1111)";
          //          +password+")";
        //    st.executeUpdate(sql);
      //  }
            //The insert new user query, it returns the ID of the added user.
            //query = new StringBuilder("INSERT INTO login (idlogin,username, password) ");
            //query.append("VALUES(?, ?,?); "); 
            //PreparedStatement insertUserStatement = dbConnection.prepareStatement(query.toString());
            //insertUserStatement.setString(1, "6");
            //insertUserStatement.setString(2, username);
            //insertUserStatement.setString(3, password);
          //  insertUserStatement.setString(3, firstName);
           // insertUserStatement.setString(4, lastName);
            //insertUserStatement.executeUpdate(query.toString());
            PreparedStatement pre;
            pre = dbConnection.prepareStatement("INSERT INTO userinfo (name, email,password,school,user_id)  values (?,?,?,?,?)");
            pre.setString(1,username);
            pre.setString(2, email);
            pre.setString(3, password);
            pre.setString(4,school);
            pre.setString(5, null);
	 //   pre.setBinaryStream(4, fis, (int) picfile.length());
	    int count = pre.executeUpdate();
            //Assigning a local variable holding the user's ID
         //   query = new StringBuilder("SET @idlogin = last_insert_id(); ");
        //    PreparedStatement setIDStatement = dbConnection.prepareStatement(query.toString());
        //    setIDStatement.executeUpdate();

            //Creating a new deck for the user.
         //   query = new StringBuilder("INSERT INTO NeosSQL.Decks values (); ");
         //   PreparedStatement insertDeckStatement = dbConnection.prepareStatement(query.toString());
         //   insertDeckStatement.executeUpdate();

            //Inserting the default user info to the new user's info.
        //    query = new StringBuilder("INSERT INTO NeosSQL.UserInfo (id, gold, deck) ");
        //    query.append("VALUES(@userID, 0, last_insert_id()); ");
       //     PreparedStatement insertUserInfoStatement = dbConnection.prepareStatement(query.toString());
       //     insertUserInfoStatement.executeUpdate();

            //Inserting the default card to the new user's collection.
         //   query = new StringBuilder("INSERT INTO NeosSQL.User_Card_Relation (user_id, card_id) ");
         //   query.append("VALUES(@userID, 0); ");
         //   PreparedStatement insertCardStatement = dbConnection.prepareStatement(query.toString());
         //   insertCardStatement.executeUpdate();

            //Execute the statement
            
            query = new StringBuilder("SET @user_id = last_insert_id(); ");
            PreparedStatement setIDStatement = dbConnection.prepareStatement(query.toString());
            setIDStatement.executeUpdate();
            dbConnection.commit();
            query = new StringBuilder("SELECT @user_id");
            Statement selectIDStatement = dbConnection.createStatement();
            
            ResultSet resultSet = selectIDStatement.executeQuery(query.toString());
            if(resultSet.next()){
                createdUser = getUser(resultSet.getInt("@user_id"));
            }
        //    createdUser = getUser(resultSet.getInt("@userid"));
        //    insertUserStatement.close();
         //   setIDStatement.close();
         //   insertUserInfoStatement.close();
         //   insertDeckStatement.close();
        //    insertCardStatement.close();
            
            //If the insertion completes successfully, we'll query the last user ID
            
            //Selecting the resulting User ID
        //    query = new StringBuilder("SELECT @idlogin");
        //    Statement selectIDStatement = dbConnection.createStatement();
        //    
         //   ResultSet resultSet = selectIDStatement.executeQuery(query.toString());
        //    if(resultSet.next()){
        //        createdUser = getUser(resultSet.getInt("@idlogin"));
        //    }
        //    else{
        //        throw new DBException("Error adding user");
        //    }
        }
        catch(SQLException ex){
            if(dbConnection != null){
                dbConnection.rollback();
            }
        }
            
       //     throw new DBException(String.format("User already exists!"));
       // }
        //finally{
        //    dbConnection.setAutoCommit(true);
       // }
        
        return createdUser;
    }
    public int getId(){
        return id;
    }
    
    public String getUsername(){
        return username;
    }
    


    //Can be negative to reduce gold
    public boolean giveGold(int goldToGive) throws SQLException, DBException {
        boolean goldGiven = false;
        
        dbConnection.setAutoCommit(false);
        
        try{
            StringBuilder query = new StringBuilder("UPDATE NeosSQL.UserInfo SET ");
            query.append("NeosSQL.UserInfo.gold = NeosSQL.UserInfo.gold + ? "); 
            query.append("WHERE UserInfo.id = ?;");

            PreparedStatement statement = dbConnection.prepareStatement(query.toString());
            statement.setInt(1, goldToGive);
            statement.setInt(2, id);
            statement.executeUpdate();

            dbConnection.commit();

            statement.close();
            
            goldGiven = true;
        }
        catch(SQLException ex){
            if(dbConnection != null){
                dbConnection.rollback();
            }
            
            throw new DBException(String.format("User not found!"));
        }
        finally{
            dbConnection.setAutoCommit(true);
        }
        
        return goldGiven;
    }

    @Override
    public String toString() {
        return String.format("%d %d %s", id, username);
    }

    public List<Integer> getCardCollection() throws SQLException, DBException {
        List<Integer> cardCollection = new ArrayList<Integer>();
        
        StringBuilder query = new StringBuilder("SELECT NeosSQL.User_Card_Relation.card_id ");
        query.append("FROM NeosSQL.User_Card_Relation "); 
        query.append(String.format("WHERE NeosSQL.User_Card_Relation.user_id = %d;", id));

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query.toString());

            while(resultSet.next()){                
                cardCollection.add(resultSet.getInt("card_id"));
            }

            statement.close();
        }
        
        return cardCollection;
    }
    
    public List<Integer> getDeck() throws SQLException{
        List<Integer> cardCollection = new ArrayList<Integer>();
        
        StringBuilder query = new StringBuilder("SELECT NeosSQL.Decks.card_id_1, ");
        for(int i = 2; i <= CARDS_IN_DECK; ++i){
            query.append(String.format("NeosSQL.Decks.card_id_%d", i)); 
            
            if(i != CARDS_IN_DECK){
                query.append(", ");
            }
            else{
                query.append(" ");
            }
        }
        query.append("FROM NeosSQL.Decks JOIN NeosSQL.UserInfo ");
        query.append("ON NeosSQL.UserInfo.deck = NeosSQL.Decks.id ");
        query.append(String.format("WHERE NeosSQL.UserInfo.id = %d; ", id));

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query.toString());

            while(resultSet.next()){                
                for(int i = 1; i <= CARDS_IN_DECK; ++i){
                    cardCollection.add(resultSet.getInt(String.format("card_id_%d", i)));
                }
            }

            statement.close();
        }
        
        return cardCollection;
    }
    
    /*public boolean addCardToCollection(int cardID) throws SQLException, DBException{
        boolean cardAdded = false;
        
        dbConnection.setAutoCommit(false);
        
        if(Card.doesCardExist(cardID)){  
            try{
                String query = "insert into NeosSQL.User_Card_Relation (user_id, card_id) values (?, ?)";

                PreparedStatement statement = dbConnection.prepareStatement(query.toString());
                statement.setInt(1, id);
                statement.setInt(2, cardID);
                statement.executeUpdate();

                dbConnection.commit();

                statement.close();

                cardAdded = true;
            }
            catch(SQLException ex){
                if(dbConnection != null){
                    dbConnection.rollback();
                }

                throw new DBException(String.format("Card already owned!"));
            }
            finally{
                dbConnection.setAutoCommit(true);
            }
        }
        
        return cardAdded;
    }
    
    public boolean updateDeck(HashSet<Integer> cardIDs) throws SQLException, DBException{
        boolean deckUpdated = false;
                
        for(int cardID : cardIDs){
            if(!Card.doesCardExist(cardID)){
                throw new DBException("Invalid card request");
            }
        }
        
        if(cardIDs.size() != CARDS_IN_DECK){
            throw new DBException("Duplicate cards selected");
        }
        
        if(cardIDs.size() == CARDS_IN_DECK){
            dbConnection.setAutoCommit(false);

            try{
                StringBuilder query = new StringBuilder("UPDATE NeosSQL.Decks SET ");
                for(int i = 1; i <= CARDS_IN_DECK; ++i){
                    query.append(String.format("NeosSQL.Decks.card_id_%d = ?", i));
                
                    if(i != CARDS_IN_DECK){
                        query.append(", ");
                    }
                    else{
                        query.append(" ");
                    }
                }
                query.append("WHERE NeosSQL.Decks.id = ?");

                PreparedStatement statement = dbConnection.prepareStatement(query.toString());
                int i = 1;
                for(int cardID : cardIDs){
                    statement.setInt(i, cardID);
                    ++i;
                }
                statement.setInt(CARDS_IN_DECK+1, getDeckID());
                statement.executeUpdate();

                dbConnection.commit();

                statement.close();

                deckUpdated = true;
            }
            catch(SQLException ex){
                if(dbConnection != null){
                    dbConnection.rollback();
                }

                throw new DBException(String.format("User not found!"));
            }
            finally{
                dbConnection.setAutoCommit(true);
            }
        }
        
        return deckUpdated;
    }

    public boolean removeCardFormCollection(int cardID) throws DBException, SQLException {
        boolean cardRemoved = false;
        
        if(cardID == 0){
            throw new DBException("This is not a valid card to sell");
        }
        
        dbConnection.setAutoCommit(false);
        
        try{
            StringBuilder query = new StringBuilder("DELETE FROM NeosSQL.User_Card_Relation ");
            query.append("WHERE NeosSQL.User_Card_Relation.user_id = ? "); 
            query.append("AND NeosSQL.User_Card_Relation.card_id = ?; ");
 
            PreparedStatement statement = dbConnection.prepareStatement(query.toString());
            statement.setInt(1, id);
            statement.setInt(2, cardID);
            statement.executeUpdate();

            dbConnection.commit();

            statement.close();
            
            cardRemoved = true;
        }
        catch(SQLException ex){
            if(dbConnection != null){
                dbConnection.rollback();
            }
            
            throw new DBException(String.format("Card not found for user!"));
        }
        finally{
            dbConnection.setAutoCommit(true);
        }
        
        return cardRemoved;
    }

    private Integer getDeckID() throws SQLException, DBException {
        Integer deckID = null;
        
        String query = String.format("SELECT NeosSQL.UserInfo.deck FROM NeosSQL.UserInfo WHERE NeosSQL.UserInfo.id  = %d", id);
        
        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){                
                deckID = resultSet.getInt("deck");
            }
            else{
                throw new DBException("User not found!");
            }

            statement.close();
        }
        
        return deckID;
    }*/
}
