package com.cinekancha.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cinekancha.R;
import com.cinekancha.bus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Unbinder mUnbinder;
    protected CompositeDisposable compositeDisposable;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        if (compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();

        if (toolbar != null)
            setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean isRegisterForRestEvents() {
        return false;
    }

    protected boolean isRegisterForEvents() {
        return false;
    }

    protected boolean isButterKnifeUnbind() {
        return true;
    }


    @Override
    protected void onStart() {
        if (isRegisterForEvents())
            EventBus.register(this);
        if (isRegisterForRestEvents())
            EventBus.registerRestObject(this);
        super.onStart();
    }


    @Override
    protected void onStop() {
        if (isRegisterForEvents())
            EventBus.unregister(this);
        if (isRegisterForRestEvents())
            EventBus.unregisterRestObject(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        cancelRequest();

        if (isButterKnifeUnbind())
            mUnbinder.unbind();
        super.onDestroy();
    }

    protected void cancelRequest(){
        //dispose subscriptions
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
