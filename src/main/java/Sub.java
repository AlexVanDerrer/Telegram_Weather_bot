import java.sql.SQLException;

class Sub {

    private static boolean sub;

    Sub(boolean sub){
        this.sub = sub;
    }

    // подписаться

    boolean Subscribe(){
            return sub = true;
    }
    // отписаться
    boolean  Unsubscribe(){
        return sub = true;
    }

}
