package com.lida.xmlPaser;

import java.util.ArrayList;
import java.util.List;

public class processXML {
    public List<String> Leg_list = new ArrayList<String>();
    public List<String> Origin_list = new ArrayList<String>();
    public List<String> Dst_list = new ArrayList<String>();
    public List<String> Time_list = new ArrayList<String>();

    private int start_m = 0;
    private int start_h = 0;
    private int arr_m = 0;
    private int arr_h = 0;



    public void xmlParserProcess(String xmlIn) throws Exception
    {

        String Leg = "";
        String Origin = "";
        String Dst = "";
        String[] arrOfxml = xmlIn.split("\n");
        for (String content : arrOfxml){
            if (content.contains("Leg") && !content.contains("/leg")) {
                String sub_content = content.replace("<","").replace(">", "");
                //System.out.println(sub_content);

                if (sub_content.contains("type=\"WALK\"")){
                    Leg_list.add("<b><font size=18 color=cc0029>Walk");
                }else if (sub_content.contains("type=\"BUS\"")){
                    String[] sub_string_arr = sub_content.split("\"");
                    Leg_list.add("<b><font size=18 color=cc0029>" + sub_string_arr[7] + "<br />"
                            + "<b><font size=13 color=cc0029>" + sub_string_arr[1] + "</b></font>" +
                            " Direction: <b><font size=13 color=cc0029>" + sub_string_arr[11]+ "</b></font>");
                }else if (sub_content.contains("type=\"TRAM\"")){
                    String[] sub_string_arr = sub_content.split("\"");
                    Leg_list.add("<b><font size=18 color=cc0029>" + sub_string_arr[7] + "<br />"
                            + "<b><font size=13 color=cc0029>" + sub_string_arr[1] + "</b></font>" +
                            " Direction: <b><font size=13 color=cc0029>" + sub_string_arr[11]+ "</b></font>");
                }


            }




            if (content.contains("Origin") && !content.contains("/Origin")) {
                String sub_content = content.replace("<","").replace(">", "");
                //System.out.println(sub_content);

                if (sub_content.contains("type=\"ST\"")) {
                    if (sub_content.contains("routeIdx=")){
                        String[] sub_string_arr = sub_content.split("\"");
                        Origin_list.add("From: " + sub_string_arr[1] + "  (Track: " + sub_string_arr[13]  + ")  " + sub_string_arr[9]);
                        start_m = Integer.parseInt(sub_string_arr[9].split(":")[1]);
                        start_h = Integer.parseInt(sub_string_arr[9].split(":")[0]);

                    }else{
                        String[] sub_string_arr = sub_content.split("\"");
                        Origin_list.add("From: " + sub_string_arr[1] + "  (Track: " + sub_string_arr[11]  + ")  "  + sub_string_arr[7]);
                        start_m = Integer.parseInt(sub_string_arr[7].split(":")[1]);
                        start_h = Integer.parseInt(sub_string_arr[7].split(":")[0]);
                    }
                }else if (sub_content.contains("type=\"ADR\"")) {
                    String[] sub_string_arr = sub_content.split("\"");
                    Origin_list.add("From: " + sub_string_arr[1]  + "  "  + sub_string_arr[5]);
                    start_m = Integer.parseInt(sub_string_arr[5].split(":")[1]);
                    start_h = Integer.parseInt(sub_string_arr[5].split(":")[0]);
                }


            }

            if (content.contains("Destination") && !content.contains("/Destination")) {
                String sub_content = content.replace("<","").replace(">", "");


                if (sub_content.contains("type=\"ST\"")) {
                    if (sub_content.contains("routeIdx=")){
                        String[] sub_string_arr = sub_content.split("\"");
                        Dst_list.add("To: " + sub_string_arr[1] + "  (Track: " + sub_string_arr[13] + ")  " + sub_string_arr[9]);
                        arr_m = Integer.parseInt(sub_string_arr[9].split(":")[1]);
                        arr_h = Integer.parseInt(sub_string_arr[9].split(":")[0]);

                        int time_min = (arr_h - start_h) * 60 + (arr_m - start_m);
                        Time_list.add(String.valueOf(time_min));

                    }else{
                        String[] sub_string_arr = sub_content.split("\"");
                        Dst_list.add("To: " + sub_string_arr[1] + "  (Track: " + sub_string_arr[11] + ")  "  + sub_string_arr[7]);
                        arr_m = Integer.parseInt(sub_string_arr[7].split(":")[1]);
                        arr_h = Integer.parseInt(sub_string_arr[7].split(":")[0]);

                        int time_min = (arr_h - start_h) * 60 + (arr_m - start_m);
                        Time_list.add(String.valueOf(time_min));
                    }
                }else if (sub_content.contains("type=\"ADR\"")) {
                    String[] sub_string_arr = sub_content.split("\"");
                    Dst_list.add("To: " + sub_string_arr[1]  + "  " + sub_string_arr[5]);
                    arr_m = Integer.parseInt(sub_string_arr[5].split(":")[1]);
                    arr_h = Integer.parseInt(sub_string_arr[5].split(":")[0]);

                    int time_min = (arr_h - start_h) * 60 + (arr_m - start_m);
                    Time_list.add(String.valueOf(time_min));
                }


            }



            if (content.contains("/Trip")){
                break;
            }
        }




    }


}











