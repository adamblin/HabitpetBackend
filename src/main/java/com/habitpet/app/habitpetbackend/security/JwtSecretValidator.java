package com.habitpet.app.habitpetbackend.security;

import java.util.Base64;

public class JwtSecretValidator {
    public static void main(String[] args) {
        String secret = "pC9RssV84cjU9VdwyS6lgB5r/WLzgHkZnrk8lcApAd5k4sNQiv0C+FXx4E3/Y37C/sJsbo6YOtqAadKTVfAlYw=="; // Reemplaza con tu jwt.secret
        try {
            byte[] decodedKey = Base64.getDecoder().decode(secret.trim());
            if (decodedKey.length < 64) {
                System.out.println("La clave es demasiado corta. Usa al menos 64 bytes.");
            } else {
                System.out.println("Clave secreta válida. Longitud: " + decodedKey.length + " bytes.");
            }
        } catch (Exception e) {
            System.out.println("Error al decodificar la clave secreta: " + e.getMessage());
        }
    }
}
