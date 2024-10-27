package base;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.IOException;
import java.net.ServerSocket;

public class AppiumServerManager {
    private static AppiumDriverLocalService server;

    public static void startServer() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder
                .withIPAddress(ConfigVar.IPADDRESS)
                .usingPort(ConfigVar.PORT)
                .withArgument(GeneralServerFlag.BASEPATH, ConfigVar.PATH)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                .withArgument(GeneralServerFlag.LOG_LEVEL, "debug");

        // Start the server with the builder
        server = AppiumDriverLocalService.buildService(builder);
        server.start();
    }
    public static void stopServer() {
        if (server != null) {
            server.stop();
        }
    }

    public static String getServerUrl() {
        return server != null ? server.getUrl().toString() : null;
    }

    // Check if server is running
    public static boolean isServerRunning(int port) {
        boolean isRunning = false;
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (IOException e) {
            isRunning = true;
        } finally {
            socket = null;
        }
        return isRunning;
    }
    public static void killProcessOnPort(int port){
        try{
            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("win")){
                Runtime.getRuntime().exec("taskkill /F /IM "+port+".exe");
            } else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec("lsof -t -i:" + port + " | xargs kill -9");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void waitForServerToStart(int port) {
        int maxWaitTime = 30; // Thời gian tối đa chờ (giây)
        int pollInterval = 2; // Thời gian chờ giữa các lần kiểm tra (giây)
        int elapsedTime = 0;

        while (elapsedTime < maxWaitTime) {
            if (isServerRunning(port)) {
                System.out.println("Appium server is ready!");
                return; // Nếu server sẵn sàng, thoát khỏi phương thức
            }

            try {
                Thread.sleep(pollInterval * 1000); // Chờ trước khi kiểm tra lại
                elapsedTime += pollInterval; // Cập nhật thời gian đã chờ
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Đặt lại trạng thái interrupted
                throw new RuntimeException("Waiting for server to be ready was interrupted", e);
            }
        }

        throw new RuntimeException("Appium server did not start within " + maxWaitTime + " seconds");
    }
}
