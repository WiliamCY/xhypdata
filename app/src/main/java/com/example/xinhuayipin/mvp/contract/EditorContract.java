package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;

/**
 * @Author skygge.
 * @Date on 2019-08-22.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface EditorContract {

    interface EditorView extends IBaseView{

        void setFingerName(String name);

        void complete();
    }

    interface EditorPresent extends IBasePresenter<EditorView>{

        void getFingerprint(Long id);

        void saveFingerName(String name);

        void deleteFingerName();
    }
}
