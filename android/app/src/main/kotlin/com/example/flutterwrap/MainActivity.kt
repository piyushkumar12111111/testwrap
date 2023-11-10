package com.example.liveliness_flutter_demo;


import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;


import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;

import com.mollatech.wdsdk.SDKConnector;
import android.app.Activity;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.annotation.NonNull;

public class MainActivity extends FlutterActivity {
    
    private static final String SDKChannel = "SDKChannel";
    Activity activity;


    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        activity = this;
        createChannelForJson(flutterEngine);
    }

    public void createChannelForJson(FlutterEngine flutterEngine) {
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), SDKChannel , JSONMethodCodec.INSTANCE)
        
        .setMethodCallHandler(
                new MethodChannel.MethodCallHandler() 
                {
                    @Override
                    public void onMethodCall(MethodCall call, MethodChannel.Result result) 
                    {
                        if(call.method.equals("init"))
                        {
                            try {
                                JSONObject data = call.arguments();
                                String userId = data.getString("userId"); 
                            
                            
                                if (userId == null) 
                                {
                                    result.error("ERROR", "arguments can not null", null);
                                    return;
                                }


                                SDKConnector connector = new SDKConnector(activity);
                                connector.init( userId, (i, s, jsonObject) -> {

                                    JSONObject je = new JSONObject();
                                    try {
                                        je.put("resultCode",i);
                                        je.put("resultMessage",s);
                                        je.put("resultData",jsonObject);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                        return;
                                    }

                                    result.success(je);

                                });
                            } catch (Exception e) {
                                  result.error("ERROR", e.getMessage(), null);
                            }

                            return;
                        }
                        else if(call.method.equals("start"))
                        {
                            try {
                                SDKConnector connector = new SDKConnector(activity);
                                connector.start((i, s, jsonObject) -> {

                                    JSONObject je=new JSONObject();
                                    try {
                                        je.put("resultCode",i);
                                        je.put("resultMessage",s);
                                        je.put("resultData",jsonObject);
                                    } catch (JSONException e) {
                                         e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                        return;
                                    }
                                    result.success(je);
                                });
                            } catch (Exception e) {
                                 e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                            }

                            return;
                        }
                        else if(call.method.equals("deInit"))
                        {
                            try {
                                SDKConnector connector = new SDKConnector(activity);
                                connector.deInit((i, s, jsonObject) -> {

                                    JSONObject je=new JSONObject();
                                    try {
                                        je.put("resultCode",i);
                                        je.put("resultMessage",s);
                                        je.put("resultData",jsonObject);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                        return;
                                    }
                                    result.success(je);
                                });
                            } catch (Exception e) {
                                 e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                        return;
                            }
                            return;
                        }
                        else if(call.method.equals("faceRecognition"))
                        {
                            try {

                                  JSONObject data = call.arguments();

                                String userId = data.getString("userId"); 
                                float confidence = BigDecimal.valueOf(data.getDouble("confidence")).floatValue();

                                if (userId == null ) 
                                {
                                    result.error("ERROR", "arguments can not null", null);
                                    return;
                                }

                                SDKConnector connector = new SDKConnector(activity);
                                connector.faceRecognition(confidence, (i, s, jsonObject) -> {

                                    JSONObject je=new JSONObject();
                                    try {
                                        je.put("resultCode",i);
                                        je.put("resultMessage",s);
                                        je.put("resultData",jsonObject);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                        return;
                                    }
                                    result.success(je);
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                      
                            }
                            return;
                        }
                        else if(call.method.equals("faceAuthentication"))
                        {
                            try {

                                JSONObject data = call.arguments();

                                String userId = data.getString("userId"); 
                                 float confidence = BigDecimal.valueOf(data.getDouble("confidence")).floatValue();


                                  if (userId == null) 
                                {
                                    result.error("ERROR", "arguments can not null", null);
                                    return;
                                }

                                SDKConnector connector = new SDKConnector(activity);
                                connector.faceAuthentication(userId, confidence, (i, s, jsonObject) -> {

                                    JSONObject je=new JSONObject();
                                    try {
                                        je.put("resultCode",i);
                                        je.put("resultMessage",s);
                                        je.put("resultData",jsonObject);
                                    } catch (JSONException e) {
                                         e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                        return;
                                    }
                                    result.success(je);
                                });
                            } catch (Exception e) {
                                  e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                        
                            }
                            return;
                        }
                        else if(call.method.equals("faceDelete"))
                        {
                            try {

                                JSONObject data = call.arguments();

                                String userId = data.getString("userId"); 

                                if (userId == null) 
                                {
                                    result.error("ERROR", "arguments can not null", null);
                                    return;
                                }

                                SDKConnector connector = new SDKConnector(activity);
                                connector.faceDelete(userId, (i, s, jsonObject) -> {

                                    JSONObject je=new JSONObject();
                                    try {
                                        je.put("resultCode",i);
                                        je.put("resultMessage",s);
                                        je.put("resultData",jsonObject);
                                    } catch (JSONException e) {
                                         e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                        return;
                                    }
                                    result.success(je);
                                });
                            } catch (Exception e) {
                                 e.printStackTrace();
                                        result.error("ERROR", e.getMessage(), null);
                                        return;
                            }
                            return;
                        }


                        result.notImplemented();
                    }
                });
   
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}