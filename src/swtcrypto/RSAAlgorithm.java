package swtcrypto;
import java.math.BigInteger;

public class RSAAlgorithm {
	//variables
	public int p;
	public int q;
	public int publicKey;
	public int privateKey;
	public int n;
	public int fn;
	public int M;
	public int C;
	
	public RSAAlgorithm() {
		this.p = 11;
		this.q = 101;
		this.publicKey = 7;
	}
	
	public RSAAlgorithm(int p, int q, int pk){
		this.p = p;
		this.q = q;
		this.publicKey = pk;
	}
	
	public int Crypt(int data) {
		M = data;
		n = p*q;
		fn = (p-1)*(q-1);
		privateKey = calculatePrivateKey(publicKey, fn);
		C = (int)(Math.pow(M, publicKey)%n);
		return C;
	}
	
	public int Decrypt(int data) {
		C = data;
		n = p*q;
		fn = (p-1)*(q-1);
		privateKey = calculatePrivateKey(publicKey, fn);
		M = BigInteger.valueOf(C).modPow(BigInteger.valueOf(privateKey), BigInteger.valueOf(n)).intValue();
		return M;
	}
	
	public int Decrypt(int data, int privKey, int nn) {
		return BigInteger.valueOf(data).modPow(BigInteger.valueOf(privKey), BigInteger.valueOf(nn)).intValue();
	}
	
	public int calculatePrivateKey(int e, int fn) {
		int d = 0, i=1;
		while((d*e)%fn != 1%fn)
		{
			d = (fn*i+1)/e;
			i++;
		}
		return d;
	}
	
}
