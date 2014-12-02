/*
 * Copyright 2013 Thomas Hoffmann
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.healthslife.pedometer.background;


import com.healthslife.pedometer.main.Database;
import com.healthslife.pedometer.util.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ShutdownRecevier extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
     

        context.startService(new Intent(context, SensorListener.class));

        // if the user used a root script for shutdown, the DEVICE_SHUTDOWN
        // broadcast might not be send. Therefore, the app will check this
        // setting on the next boot and displays an error message if it's not
        // set to true
        context.getSharedPreferences("pedometer", Context.MODE_MULTI_PROCESS).edit()
                .putBoolean("correctShutdown", true).commit();

        Database db = Database.getInstance(context);
        db.updateSteps(Util.getToday(), db.getCurrentSteps());
        // current steps will be reset on boot @see BootReceiver
        db.close();
    }

}