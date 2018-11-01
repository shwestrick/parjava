class StrGen {

  public static long hashInt(int idx) {
    long v = (long)idx * 3935559000370003845L + 2691343689449507681L;
    v = v ^ (v >> 21);
    v = v ^ (v << 37);
    v = v ^ (v >> 4);
    v = v * 4768777513237032717L;
    v = v ^ (v << 20);
    v = v ^ (v >> 41);
    v = v ^ (v << 5);
    return v;
  }

  public static char[] generate(int idx) {
    long v = hashInt(idx);
    int hashed = (int)(v % 10000000);
    return Integer.toBinaryString(hashed).toCharArray();
  }

  public static boolean pred(char[] e) {
    int cnt = 0;
    for (int i = 0;i < e.length;i++) { 
      if (e[i] == '1'){
        cnt++;
      }
    }
    return (cnt % 2) == 0;
  }

  public static char xor (char x, char y) {
    if (x == '1' && y == '0') {
      return '1';
    }
    if (x == '0' && y == '1') {
      return '1';
    }
    return '0';
  }

  public static char[] combine(char[] x, char[] y) {
    char[] result = new char[Math.max(x.length, y.length)];

    for (int i = 0;i < Math.max(x.length, y.length);i++){
      char xi = '0';
      char yi = '0';
      if (i < x.length) {
        xi = x[i];
      }
      if (i < y.length) {
        yi = y[i];
      }
      result[i] = xor(xi, yi);
    }
    return result;
  }

  public static int compare(char[] x, char[] y) {
    return String.valueOf(x).compareTo(String.valueOf(y));
  }

  public static int hash(char[] x) {
    long v = hashInt(Integer.parseInt(String.valueOf(x), 2)) % 10000000;
    return (int) v;
  }
}
