package sillyv.com.counterlists.screens;

import sillyv.com.counterlists.baseline.BaseView;

/**
 * Created by Vasili.Fedotov on 2/24/2017.
 */

public interface Contracts {


    interface UpsertContract {
        interface UpsertModel {


            String getDefaultValue();

            void setDefaultValue(String defaultValue);

            String getName();

            void setName(String name);

            String getNote();

            void setNote(String note);

            int getBackgroundColor();

            void setBackgroundColor(int backgroundColor);

            int getClickSound();

            void setClickSound(int clickSound);

            int getVibrate();

            void setVibrate(int vibrate);

            int getSpeakValue();

            void setSpeakValue(int speakValue);

            int getSpeakName();

            void setSpeakName(int speakName);

            int getKeepAwake();

            void setKeepAwake(int keepAwake);

            int getUseVolume();

            void setUseVolume(int useVolume);

            String getDateCreated();

            void setDateCreated(String dateCreated);

            String getDateModified();

            void setDateModified(String dateModified);

            String getLastUsed();

            void setLastUsed(String lastUsed);

            String getToolbarTitle();

            void setToolbarTitle(String toolbarTitle);
        }
        interface UpsertViewContract<D>
                extends BaseView<D> {



        }
    }
}
