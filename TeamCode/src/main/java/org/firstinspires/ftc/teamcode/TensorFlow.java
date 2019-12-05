
package org.firstinspires.ftc.robotcontroller.external.samples;



import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;

import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;



/**

 * This 2019-2020 OpMode illustrates the basics of using the TensorFlow Object Detection API to

 * determine the position of the Skystone game elements.

 *

 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.

 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.

 *

 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as

 * is explained below.

 */

@Autonomous(name = "Concept: TensorFlow", group = "Concept")

@Disabled

public class TensorFlow extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";

    private static final String LABEL_FIRST_ELEMENT = "Stone";

    private static final String LABEL_SECOND_ELEMENT = "Skystone";



    /*

     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which

     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.

     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer

     * web site at https://developer.vuforia.com/license-manager.

     *

     * Vuforia license keys are always 380 characters long, and look as if they contain mostly

     * random data. As an example, here is a example of a fragment of a valid key:

     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...

     * Once you've obtained a license key, copy the string from the Vuforia web site

     * and paste it in to your code on the next line, between the double quotes.

     */

    private static final String VUFORIA_KEY =

            "AbytQHT/////AAABmeMj3vqrdEBapjWdgR50q+RGJfE3CznIfr0p4ni7gvGlyfUvbVIsvCLVQqQ7vcHgIGnE7/U7XbLRYgg0rOuQgS0D2BF56wBE3ZcC9yFkucC9swuTWl2KGWGFr3e2bmGb9zoubbclXBDHei/J9/5b/NjoM4+AbhbejG55DyD68Y+IhcQqFYKXxng91Mun5vUPj6w8ReL6kIaymEyqp3vcN1BkUdg1t+DuJ588QswnOhCunl8ymlz1ck87UmlYWc61+rLxSzHbAOT24b+quZlxlu2CWmFMF15g60ojajffPbg9Mk4l+x3RA/9gs+UdPmmDXIfj39wApzPLQ8XRRJG/mpjsn9vIHmKN1KO4ESoREJyP ";



    /**

     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia

     * localization engine.

     */

    private VuforiaLocalizer vuforia;



    /**

     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object

     * Detection engine.

     */

    private TFObjectDetector tfod;



    @Override

    public void runOpMode() {

        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that

        // first.

        initVuforia();



        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {

            initTfod();

        } else {

            telemetry.addData("Sorry!", "This device is not compatible with TFOD");

        }



        /**

         * Activate TensorFlow Object Detection before we wait for the start command.

         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.

         **/

        if (tfod != null) {

            tfod.activate();

        }



        /** Wait for the game to begin */

        telemetry.addData(">", "Press Play to start op mode");

        telemetry.update();

        waitForStart();



        if (opModeIsActive()) {

            while (opModeIsActive()) {

                if (tfod != null) {

                    // getUpdatedRecognitions() will return null if no new information is available since

                    // the last time that call was made.

                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                    if (updatedRecognitions != null) {

                      telemetry.addData("# Object Detected", updatedRecognitions.size());



                      // step through the list of recognitions and display boundary info.

                      int i = 0;

                      for (Recognition recognition : updatedRecognitions) {

                      
                           i++;
                      
                      if(recognition.getLabel() == "Skystone") {
                      double midH = (recognition.getLeft()+recognition.getRight())/2;
                      double midV = (recognition.getTop()+recognition.getBottom())/2;
                      if (75 <= midH && midH <= 180 && 75 <= midV && midV <= 180){
                      }else if(75 <= midH && midH  <= 300 && 75 <= midV && midV <= 180) {
                     } else if (400 <= midH && midH <= 500 && 75 <= midV && midV <= 180) {
                     }
                      }
                      
                 }

                      telemetry.update();

                    }

                }

            }

        }



        if (tfod != null) {

            tfod.shutdown();

        }

    }



    /**

     * Initialize the Vuforia localization engine.

     */

    private void initVuforia() {

        /*

         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.

         */

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();



        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        parameters.cameraDirection = CameraDirection.BACK;



        //  Instantiate the Vuforia engine

        vuforia = ClassFactory.getInstance().createVuforia(parameters);



        // Loading trackables is not necessary for the TensorFlow Object Detection engine.

    }



    /**

     * Initialize the TensorFlow Object Detection engine.

     */

    private void initTfod() {

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(

            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);

        tfodParameters.minimumConfidence = 0.8;

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);

    }

}
