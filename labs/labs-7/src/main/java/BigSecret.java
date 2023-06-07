import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Optional;

//Ignore this comment, it is to stop eclise from changing the format of this code.
//@formatter:off
public final class BigSecret {

	private static final int ULTIMATE_SECRET_LENGTH = 32;
	private final long localSecretCode;
	private final byte[] ultimateKey = new byte[ULTIMATE_SECRET_LENGTH];

	public BigSecret() {
		SecureRandom sr = new SecureRandom();
		localSecretCode = sr.nextLong(); // Initialize the localSecretCode to a random long
		sr.nextBytes(ultimateKey); // Create a random ultimateKey
	}

	// Secret message (Task 1)
	public Optional<String> unlockAndGetMessage(long secret) {
		if (secret == localSecretCode) { //if the parameter secret is the same as the expected secret code
			return Optional.of(getSecretMessage());
		}
		return Optional.empty();
	}

	private final String getSecretMessage() {
		return "You found the secret message!";
	}

	// Ultimate secret message (Task 2)
	public String decodeAndGetUltimateSecret() {
		// If the ultimateKey has been tampered with, or the length of the key doesn't match
		// what is expected, then someone is trying to fool us. We ain't having any of that!
		if (ultimateKey == null || ULTIMATE_SECRET_LENGTH != ultimateKey.length) {
			throw new IllegalStateException("Someone has tinkered with the ultimate key!");
		}
		
		// We made it so far. We know the key 'ultimateKey' is intact. We create a char
		// array 'str' contianing the ultimate secret message
		char[] str = "You found the ultimate secret! :O".toCharArray();
		
		// We go through the array 'str' and for each of it's characters, we XOR it with 
		// the respective byte from the 'ultimateKey' array.
		// Basically:
		//    str[i] = str[i] XOR ultimateKey[i]
		// The loop bellow is a bit confusing on purpose :-)
		for (int i = 0; i < ULTIMATE_SECRET_LENGTH; str[i] ^= ultimateKey[i++]); 
		
		//Finally, return the (potentially scrambled) message
		return new String(str);
		
		// Remember XOR's truth table!
		// +-----------------+
		// | A | B | A XOR B |
		// +-----------------+
		// | 0 | 0 |    0    |
		// | 0 | 1 |    1    |
		// | 1 | 0 |    1    | 
		// | 1 | 1 |    0    |
		// +-----------------+
		//
		// XOR between integers (or characters) is the result of XORing each of their bits:
		//  10101 (21)
		//  11001 (25)
		// ----------- XOR
		//  01100 (12)
	}

}
