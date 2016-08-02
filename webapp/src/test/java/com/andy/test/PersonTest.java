package com.andy.test;

import com.andy.tool.BlankMan;
import com.andy.tool.Person;
import org.junit.Test;

/**
 * Created by zhangshouzheng on 2016/7/28.
 */
public class PersonTest {

    @Test
    public void getWhatIsUp(){
        Person blankMan = new BlankMan();
        blankMan.travel();
        blankMan.show();
    }
}
