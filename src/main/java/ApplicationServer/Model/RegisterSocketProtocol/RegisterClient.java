package ApplicationServer.Model.RegisterSocketProtocol;

import ApplicationServer.Controllers.ControllerConfiguration;
import ApplicationServer.Model.DataLayerModels.UserDataLayer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegisterClient extends ControllerConfiguration {
    private final int PORT = 5595;
    private final String HOST = "localhost";
    private BufferedReader in;
    private PrintWriter out;

    public RegisterClient() throws IOException {
        Socket socket = new Socket(HOST, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public ResponseEntity<?> registerUser(UserDataLayer user) throws IOException {
        out.println("register request");
        if (in.readLine().equalsIgnoreCase("User JSON?")) {
            String userJSON = jsonMapper.writeValueAsString(user);
            out.println(userJSON);
            String message = in.readLine();
            switch (message) {
                case "Username already exists":
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
                case "Account created":
                    return ResponseEntity.status(HttpStatus.OK).body(message);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
