package com.java.wangxingqi.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.wangxingqi.Manager;
import com.java.wangxingqi.R;
import com.java.wangxingqi.adapter.MessageAdapter;
import com.java.wangxingqi.bean.ResultBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NotificationsFragment extends Fragment {

    private static final String TAG = "NotificationsFragment";
    Observer<List<ResultBean>> observer = null;
    private List<Message> messageList;
    private RecyclerView msgRecyclerView;
    private EditText inputQuestion;
    private String course = "chinese";
    private MessageAdapter messageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        msgRecyclerView = root.findViewById(R.id.msg_recycler_view);
        Spinner courseSpinner = root.findViewById(R.id.course_spinner);
        inputQuestion = root.findViewById(R.id.input_question);
        Button send = root.findViewById(R.id.send_question);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        messageAdapter = new MessageAdapter(messageList = getData());
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.course, R.layout.support_simple_spinner_dropdown_item);
        courseSpinner.setAdapter(spinnerAdapter);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.setAdapter(messageAdapter);
        initObserver();
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String courseCN = adapterView.getItemAtPosition(position).toString();
//                Toast.makeText(getContext(), "?????????????????????" + courseCN, Toast.LENGTH_SHORT).show();
                switch (courseCN){
                    case "??????":
                        course = "chinese";
                        break;
                    case "??????":
                        course = "math";
                        break;
                    case "??????":
                        course = "english";
                        break;
                    case "??????":
                        course = "physics";
                        break;
                    case "??????":
                        course = "chemistry";
                        break;
                    case "??????":
                        course = "biology";
                        break;
                    case "??????":
                        course = "history";
                        break;
                    case "??????":
                        course = "politics";
                        break;
                    case "??????":
                        course = "geo";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        send.setOnClickListener(view -> {
            String content = inputQuestion.getText().toString();
            if(!content.equals("")) {
                messageList.add(new Message(content,Message.TYPE_SEND));
                messageAdapter.notifyItemInserted(messageList.size()-1);
                msgRecyclerView.scrollToPosition(messageList.size()-1);
                inputQuestion.setText("");//???????????????????????????
                Log.i(TAG, "send message:" + content);
                Manager.answerInputQuestion(course, content, observer);
            }
        });
        return root;
    }



    @NonNull
    private List<Message> getData() {
        List<Message> list = new ArrayList<>();
        list.add(new Message("Hello! Welcome to knowledge Q&A!",Message.TYPE_RECEIVED));
        return list;
    }

    private void initObserver() {
         observer = new Observer<List<ResultBean>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e(TAG,"observer subscribed");
            }
            @Override
            public void onNext(@NonNull List<ResultBean> answerList) {
                Log.e(TAG,"getAnswer");
                String content;
                if (answerList.isEmpty()||answerList.get(0).getValue().equals("")) {
                    content = "??????????????????????????????????????????????????????????????????/???????????????";
                } else {
                    content = answerList.get(0).getValue();
                }
                messageList.add(new Message(content,Message.TYPE_RECEIVED));
                messageAdapter.notifyItemInserted(messageList.size()-1);
                msgRecyclerView.scrollToPosition(messageList.size()-1);
            }
            @Override
            public void onError(@NonNull Throwable e) {
            }
            @Override
            public void onComplete() {
                Log.e(TAG,"Complete");
            }
        };
    }
}