package com.Vlad.Herescu.simplehero.logic.BusinessLogic;

import com.Vlad.Herescu.simplehero.DatabaseHandler.DatabaseHandler;
import com.Vlad.Herescu.simplehero.logic.BusinessLogic.FacebookLogic.FacebookLogic;
import com.Vlad.Herescu.simplehero.logic.BusinessLogic.FacebookLogic.FacebookLogicITf;
import com.Vlad.Herescu.simplehero.logic.BusinessLogic.FacebookLogic.FriendsManager;

/**
 * Created by admin on 2/17/2016.
 */
public class ManagerSingleton {

    private static ManagerSingleton m_instance;

    private ServerManagerItf m_serverHandler;

    private IdeasManager m_ideasHandler;

    private FacebookLogicITf m_facebook;

    private FriendsManager m_friends;




    public static ManagerSingleton getInstance() {
        if(m_instance == null)
            m_instance = new ManagerSingleton();

        return m_instance;
    }

    private ManagerSingleton()
    {
        m_serverHandler = new ServerManagerEntity();
        m_ideasHandler = new IdeasManager();
        m_facebook = new FacebookLogic();
        m_friends = new FriendsManager();
    }

    public ServerManagerItf get_ServerHandler() {
        return m_serverHandler;
    }

    public void set_ServerHandler(ServerManagerItf _send) {
        m_serverHandler = _send;
    }

    public IdeasManager get_IdeasHandler() {
        return m_ideasHandler;
    }

    public void setM_ideasHandler(IdeasManager m_ideasHandler) {
        this.m_ideasHandler = m_ideasHandler;
    }

    public FriendsManager getM_friends() {
        return m_friends;
    }

    public FacebookLogicITf get_facebook() {
        return m_facebook;
    }
}
