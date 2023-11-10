import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:convert';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter User Interface',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: UserInputForm(),
    );
  }
}

class UserInputForm extends StatefulWidget {
  @override
  _UserInputFormState createState() => _UserInputFormState();
}

class _UserInputFormState extends State<UserInputForm> {
  // TextEditingControllers for the text fields
  final serverBaseUriController = TextEditingController();
  final accountIdController = TextEditingController();
  final userIdController = TextEditingController();
  final userNameController = TextEditingController();
  final emailIdController = TextEditingController();
  final phoneNumberController = TextEditingController();
  final appIdController = TextEditingController();
  final applicationNumberController = TextEditingController();
  String selectedBranch = 'Select a branch';

  // MethodChannel to communicate with native code
  static const platformChannel = MethodChannel('SDKChannel');

  @override
  void dispose() {
    // Dispose of the controllers to free up resources
    serverBaseUriController.dispose();
    accountIdController.dispose();
    userIdController.dispose();
    userNameController.dispose();
    emailIdController.dispose();
    phoneNumberController.dispose();
    appIdController.dispose();
    applicationNumberController.dispose();
    super.dispose();
  }

  Future<void> _startProcess() async {
    try {
      // Prepare the data to send to native code
      final Map<String, dynamic> initData = {
        'serverBaseUri': serverBaseUriController.text,
        'accountId': accountIdController.text,
        'userId': userIdController.text,
        'userName': userNameController.text,
        'emailId': emailIdController.text,
        'phoneNumber': phoneNumberController.text,
        'appId': appIdController.text,
        'applicationNumber': applicationNumberController.text,
        'selectedBranch': selectedBranch,
      };

      // Call the 'init' method on the native side with the data
      final dynamic result =
          await platformChannel.invokeMethod('init', json.encode(initData));
      print(result);

      // If you want to handle the result, you can do so here
      // For example, you can display a message or update the UI
    } on PlatformException catch (e) {
      // Handle the error if the method is not implemented on the native side
      print("Failed to call method: ${e.message}");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Input Form'),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[
            TextField(
              controller: serverBaseUriController,
              decoration: InputDecoration(labelText: 'Server base URI'),
            ),
            TextField(
              controller: accountIdController,
              decoration: InputDecoration(labelText: 'Account ID'),
            ),
            TextField(
              controller: userIdController,
              decoration: InputDecoration(labelText: 'User ID'),
            ),
            TextField(
              controller: userNameController,
              decoration: InputDecoration(labelText: 'User Name'),
            ),
            TextField(
              controller: emailIdController,
              decoration: InputDecoration(labelText: 'Email ID'),
            ),
            TextField(
              controller: phoneNumberController,
              decoration: InputDecoration(labelText: 'Phone Number'),
            ),
            TextField(
              controller: appIdController,
              decoration: InputDecoration(labelText: 'App ID'),
            ),
            TextField(
              controller: applicationNumberController,
              decoration: InputDecoration(labelText: 'Application Number'),
            ),
            DropdownButton<String>(
              value: selectedBranch,
              onChanged: (String? newValue) {
                setState(() {
                  selectedBranch = newValue!;
                });
              },
              items: <String>[
                'Select a branch',
                'Branch 1',
                'Branch 2',
                'Branch 3',
              ].map<DropdownMenuItem<String>>((String value) {
                return DropdownMenuItem<String>(
                  value: value,
                  child: Text(value),
                );
              }).toList(),
            ),
            ElevatedButton(
              onPressed: _startProcess,
              child: Text('START'),
            ),
          ],
        ),
      ),
    );
  }
}
