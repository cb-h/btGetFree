package com.pci.aw.btgetfree.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pci.aw.btgetfree.R;
import com.pci.aw.btgetfree.utils.MyLog;
import com.pci.aw.btgetfree.utils.MyUtils;



@SuppressLint("ValidFragment")
public class HelpFragment extends BaseFragment {

    private Activity context;

    public HelpFragment() {
    }

    public HelpFragment(Activity context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.help_layout,container,false);



        return view;
    }

}
