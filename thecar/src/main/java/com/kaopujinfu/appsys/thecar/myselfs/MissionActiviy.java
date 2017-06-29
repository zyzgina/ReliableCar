package com.kaopujinfu.appsys.thecar.myselfs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.view.MyListView;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.MyselfMissionAdapter;
import com.kaopujinfu.appsys.thecar.myselfs.files.MissionCommitActivity;
import com.kaopujinfu.appsys.thecar.myselfs.files.MissionListsActivity;

/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/6/29.
 */

public class MissionActiviy extends Activity{
    private MyListView mMission;
    private MyselfMissionAdapter missionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        initMiss();
    }
    private void initMiss(){
        mMission = (MyListView) findViewById(R.id.mission_myself);
        missionAdapter = new MyselfMissionAdapter(this);
        mMission.setAdapter(missionAdapter);
        mMission.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MissionActiviy.this, MissionCommitActivity.class);
                startActivity(intent);
            }
        });
        TextView mMore = (TextView) findViewById(R.id.missionTvMy);
        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MissionActiviy.this, MissionListsActivity.class);
                startActivity(intent);
            }
        });
    }
}
