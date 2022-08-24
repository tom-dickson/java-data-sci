package datasci;

public class LinearReg {

  public static Matrix fit(Matrix x, Matrix y){
    Matrix firstTerm = Matrix.multiply(Matrix.transpose(x), x);
    Matrix secTerm = Matrix.multiply(Matrix.transpose(x), y);
    Matrix coeffs = Matrix.multiply(Matrix.inverse(firstTerm), secTerm);
    return coeffs;
  }

}
