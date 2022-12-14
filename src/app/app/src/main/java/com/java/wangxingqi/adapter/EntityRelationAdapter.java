package com.java.wangxingqi.adapter;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.wangxingqi.EntityViewActivity;
import com.java.wangxingqi.Fetch;
import com.java.wangxingqi.Manager;
import com.java.wangxingqi.R;
import com.bumptech.glide.Glide;
import com.java.wangxingqi.bean.EntityBean;
import com.java.wangxingqi.bean.ProblemBean;
import com.java.wangxingqi.bean.PropertyBean;
import com.java.wangxingqi.bean.RelationBean;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EntityRelationAdapter extends RecyclerView.Adapter<EntityRelationAdapter.EntityRelationViewHolder> {


    private List<RelationBean> relations;
    private String course;
    private String category = new String();

    public EntityRelationAdapter(List<RelationBean> relations, String course){
        Log.i("EntityRelation category", course);
        this.relations = relations;
        this.course = course;
    }


    @NonNull
    @Override
    public EntityRelationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.item_entity_relation, parent, false);
        return new EntityRelationViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(EntityRelationViewHolder holder, int position) {
        holder.bind(relations.get(position));
    }

    @Override
    public int getItemCount() {
        return Math.min(20, relations.size());
    }


    class EntityRelationViewHolder extends RecyclerView.ViewHolder {
        TextView mLabel;
        TextView mRelation;
        ImageView mImg;
        Observer<List<EntityBean>> observer;
        public EntityRelationViewHolder(View itemView) {
            super(itemView);
            mLabel = itemView.findViewById(R.id.entity_label2);
            mRelation = itemView.findViewById(R.id.entity_relation);
            mImg = itemView.findViewById(R.id.entity_forward);

        }
        private void initObserver() {
            observer = new Observer<List<EntityBean>>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(List<EntityBean> entities) {
                    Log.i("RelationObserver", "onNext");
                    if (entities.isEmpty()) {
                        Log.i("onNext","emptyList");
                        category = null;
                    } else {
                        Log.i("onNext","nonemptyList");
                        category = entities.get(0).getCategory();
                    }
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onComplete() {
                }
            };

        }


        public void bind(final RelationBean relation) {
            initObserver();
            boolean forward = true;
            String entity_name = relation.getObjectName();
            if(entity_name == null) {
                entity_name = relation.getSubjectName();
                forward = false;
            }
            // load relation info
            mLabel.setText(entity_name);
            mRelation.setText(relation.getRelationName());

            if(forward){
                Glide.with(mImg.getContext())
                        .load(R.drawable.ic_arrow_circle_forward_24px)
                        .into(mImg);
            } else{
                Glide.with(mImg.getContext())
                        .load(R.drawable.ic_arrow_circle_backward_24px)
                        .into(mImg);
            }

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("EntityRelation","onCLick");
                    // Go to the detailed page

                    Log.i("EntityRelationAdapter", "before searchEntity");

                    Manager.searchEntity(course, relation.getName(), null, true, observer);
                    Log.i("EntityRelationAdapter", "after searchEntity");
                    if (category == null) {
                        List<EntityBean> beans = EntityBean.findWithQuery(EntityBean.class, "SELECT * FROM ENTITY_BEAN WHERE uri = " + "'" + relation.getEntityUri()+ "'");
                        if (beans.isEmpty()) {
                            Toast.makeText(view.getContext(), "?????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            System.out.println("have this relation entity in db");
                            category = beans.get(0).getCategory();
                        }
                    }
                    Intent intent = new Intent(view.getContext(), EntityViewActivity.class);
                    intent.putExtra("entity_label", relation.getName());
                    intent.putExtra("entity_course",course);
                    intent.putExtra("entity_category", category);
                    intent.putExtra("entity_uri", relation.getEntityUri());
                    view.getContext().startActivity(intent);
                }
            });

        }
    }
}
