import 'dart:convert';
import 'package:word_events/globals/api_constants.dart';
import 'package:word_events/models/activity.dart';
import 'package:http/http.dart' as http;

//final ActivityController activityController = Get.find<ActivityController>();

class ActivityService {
  // Create (POST)
  Future<http.Response> postActivity(Activity activity) async {
    try {
      final response = await http.post(
        Uri.parse(activitiePostUrl),
        headers: <String, String>{
          'Authorization': 'Bearer $accessToken',
          'Content-Type' : 'application/json; charset=UTF-8',
        },
        body: jsonEncode(
          activity.toJson(),
        ),
      );
      print(response.body);
      return response;
    } catch (error){
      throw Exception("Failed when create a json data ! Error: $error");
    }

  }

  // Read (GET)
  Future<List<Activity>> getActivities() async {
    try {
      //final response = await http.get(Uri.parse(activitiesGetUrl));
      final response = await http.get(
          Uri.parse(activitiesGetUrl),
          headers: {
      'Authorization': 'Bearer $accessToken',
      'Content-Type': 'application/json',
      },
      );

      if(response.statusCode == 200) {
        print(response.body);
        List<dynamic> responseList = json.decode(response.body);

        List<Activity> activities = responseList.map((jsonItem) => Activity.fromJson(jsonItem)).toList();
        return activities;
      }
      else {
        throw Exception("Failed to connect");
      }
    } catch (error) {
      print('Error: $error');
      throw Exception('Failed to load activities ! Error: $error');
    }

  }

  // Update (PUT)
  Future<http.Response> updateActivity(Activity activity, int id) async{
    try{

      final response = await http.put(Uri.parse("$activitiePutUrl$id"),
          headers: <String, String>{
            'Authorization': 'Bearer $accessToken',
            'Content-Type' : 'application/json; charset=UTF-8',
          },
          body: jsonEncode(
              activity.toJson(),
          )
      );
      if(response.statusCode == 200){
        return response;
      }
      else {
        print("Serviste hata oldu : ${response.statusCode}");
        throw Exception("Fail fail error: ${response.statusCode}");
      }
    } catch(error) {
      throw Exception("Failed when to update ! Error: $error");
    }
  }

  // Delete
  Future<http.Response> deleteActivity(int id) async{
    try {
      final response = http.delete(Uri.parse("$activitieDeleteUrl$id"),
      headers: <String,String>{
        'Authorization': 'Bearer $accessToken',
        'Content-Type' : 'application/json; charset=UTF-8',
      },
      );
      return response;
    } catch(error) {
      throw Exception("Failed when delete json data ! Error: $error");
    }
  }
}