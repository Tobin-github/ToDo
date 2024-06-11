package top.tobin.component.eventbus;

/**
 * 组件通信Event类
 * @param <T>
 */
public class CommonCompEvent<T> {
    private String messageKey;
    private T body;
    public CommonCompEvent(String messageKey, T body){
        this.messageKey = messageKey;
        this.body = body;
    }
    public String getMessageKey() {
        return this.messageKey;
    }


    public T getBody() {
        return body;
    }

}
