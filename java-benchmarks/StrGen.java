class StrGen {
  public static char[] generate(int idx) {
    int length = idx & 0x7f;
    char[] res = new char[length];
    for (int i = 0;i < length;i++) {
      res[i] = 'a';
    }
    return res;
  }

  public static boolean pred(char[] e) {
    int cnt = 0;
    for (int i = 0;i < e.length;i++) { 
      cnt++;
    }
    return (cnt & 1) == 0;
  }

  public static char[] combine(char[] x, char[] y) {
    for (int i = 0; i < Math.max(x.length, y.length);i++) {
      if (i >= x.length) {
        return y;
      } else if (i >= y.length) {
	return x;
      } else if (x[i] < y[i]) {
	return y;
      } else if (x[i] > y[i]) {
	return x;
      }
    }
    return x;
  }

  public static int compare(char[] x, char[] y) {
    for (int i = 0; i < Math.max(x.length, y.length);i++) {
      if (i >= x.length) {
        return -1;
      } else if (i >= y.length) {
	return 1;
      } else if (x[i] < y[i]) {
	return -1;
      } else if (x[i] > y[i]) {
	return 1;
      }
    }
    return 0;
  }

  public static int hash(char[] x) {
	  return 0;
  }
}
