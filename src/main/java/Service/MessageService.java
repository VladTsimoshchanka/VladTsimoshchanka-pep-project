package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    public Message newMessage(Message message){
        return messageDAO.addMessage(message);
    }
    public Message getMessageById(int id){
        return messageDAO.lookUpMessageById(id);
    }
    public Message updateMessage(int id, String text){
        return messageDAO.updateMessage(id, text);
    }
    public Message removeMessage(int id){
        return messageDAO.deleteMessage(id);
    }
    public List<Message> getMessagesByAccountId(int id){
        return messageDAO.lookUpMessagesByAccountId(id);
    }
    public List<Message> getMessages(){
        return messageDAO.getAllMessages();
    }
}
