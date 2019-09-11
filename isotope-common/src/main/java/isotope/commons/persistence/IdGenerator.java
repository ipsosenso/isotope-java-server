/*
 * Isotope 1.6
 * Copyright (C) 2019 IpsoSenso
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package isotope.commons.persistence;

import java.net.InetAddress;
import java.util.Random;

/**
 * Reprise de la classe de génération des identifiants aléatoires
 * créé initialement dans Cardibox
 *
 * Created by oturpin on 24/06/16.
 */
public class IdGenerator {

    private static long counterMax = 0;
    private static long counter = counterMax+1;

    private static void calcCounter() {

        // bits 33 -> 62 (30) : current time in seconds
        counter = ((System.currentTimeMillis() / 1000) & 0x3FFFFFFFL) << 33;

        String ip = "127.0.0.1";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Throwable t) {
            // Don't care any error
        }

        // bits 25 -> 32 (8)
        if (ip.equals("127.0.0.1")) {
            // 127.0.0.1 is too common. Instead, generate a unique string.
            counter |= (((long)(new Random().nextInt(256))) & 0xFFL) << 25;
        } else {
            counter |= (Long.parseLong(ip.substring(ip.lastIndexOf(".")+1)) & 0xFF) << 25;
        }

        // bits 21 -> 24 (3)
        counter |= ( ((long)(new Random().nextInt(16))) & 0x0FL) << 21;

        // All remaining bits 0 -> 20 (21) are the counter

        // It will count until it reach
        counterMax = counter + 0x1FFFFF; // 21 bits
    }

    /**
     *
     */
    public synchronized static long generate() {
        if (counter>counterMax) {
            calcCounter();
        }
        return counter++;
    } // generate
}
