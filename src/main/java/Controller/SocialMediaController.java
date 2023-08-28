package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import DAO.AccountDAO;
import Service.MessageService;
import DAO.MessageDAO;
import java.util.ArrayList;
import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageServie;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageServie = new MessageService();
        
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::addMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        //System.out.println(acc.getUsername() + " " + acc.getPassword());
        Account newAcc = accountService.registerAccount(acc);
        if(newAcc!=null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(newAcc));
        }else{
            ctx.status(400);
        }
    }
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message foundMessage = messageServie.getMessageById(message_id);
        if(foundMessage!=null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(foundMessage));
        }else{
            ctx.status(200);
        }
        
        
        
    }
    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        
        String newText = ctx.body();
        //System.out.println(newText);
        //if(newText != ""){
            newText = newText.replace("{", "");
            newText = newText.replaceAll("\"", "");
            newText = newText.replace(": ", "");
            newText = newText.replace(" }", "");
            newText = newText.replace("message_text", "");

        //}
        //System.out.println("Amended text: " + newText);
        Message updatedMessage = messageServie.updateMessage(message_id, newText);
        if(updatedMessage!=null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }else{
            ctx.status(400);
        }
        
        
        
    }
    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message removedMessage = messageServie.removeMessage(message_id);
        if(removedMessage!=null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(removedMessage));
        }else{
            ctx.status(200);
        }
          
    }
    private void getMessagesByAccountIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int acc_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> foundMessages = messageServie.getMessagesByAccountId(acc_id);
        
        ctx.status(200);
        ctx.json(mapper.writeValueAsString(foundMessages));
          
    }
    private void getMessagesHandler(Context ctx) throws JsonProcessingException {
        
        List<Message> allMessages = messageServie.getMessages();
        ctx.status(200);
        ctx.json(allMessages);
        
    }
    private void addMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        //System.out.println(acc.getUsername() + " " + acc.getPassword());
        Message newMessage = messageServie.newMessage(message);
        if(newMessage!=null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(newMessage));
        }else{
            ctx.status(400);
        }
    }
    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        //System.out.println(acc.getUsername() + " " + acc.getPassword());
        Account foundAcc = accountService.verifyAccount(acc);
        if(foundAcc!=null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(foundAcc));
        }else{
            ctx.status(401);
        }
    }
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}