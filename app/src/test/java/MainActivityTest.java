import android.view.View;

import com.example.photo500px.BuildConfig;
import com.example.photo500px.MainActivity;
import com.example.photo500px.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by igor on 07.12.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    @Test
    public void checkLaunch() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void checkAvailableViews() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        assertNotNull(shadowOf(activity).findViewById(R.id.toolbar));
        assertNotNull(shadowOf(activity).findViewById(R.id.sliding_tabs));
        assertNotNull(shadowOf(activity).findViewById(R.id.viewpager));
    }
}
