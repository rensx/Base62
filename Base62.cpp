#include<iomanip>
#include<iostream>
#include<tuple>
#include<vector>
#include<algorithm>
#include<string>

using namespace std;
#define getPos(c)((c) >= '0' && (c) <= '9' ? (c) - '0' : ((c) >= 'a' && (c) <= 'z' ? (c) - 'a' + 10 : (c) - 'A' + 36))
#define getChar(c)((c) >= 0 && (c) < 10 ? (char)((c) + '0') : ((c) >= 10 && (c) < 36 ? (char)((c) + 'a' - 10) : (char)((c) + 'A' - 36)))
#define makeInt(c)( sizeof(c)==8 ? *((int64_t * ) &(c)): * ((int32_t * ) &(c)) )
#define makeDF(n,c)( (c)==8 ? *((double * ) &(n)): * ((float * ) &(n)) )
//const std::string alphnum = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
std::string encode(int64_t n, int b) {
    if (n == 0) {
        return "0";
    }
    bool negative = n < 0;
    if (negative && b > 0) {
        n = -n;
    }
    std::string output;
    int64_t nn = n;
    while (nn != 0) {
        int rem = nn % b;
        nn /= b;
        if (rem < 0) {
            nn++;
            rem -= b;
        }
        output += getChar(rem);//alphnum[rem]
    }
    if (negative) output += '-';
    std::reverse(output.begin(), output.end());
    return output;
}
int64_t decode(const std::string & s, int b) {
    std::string ns = s;
    int absBase = b < 0 ? -b : b;
    if (ns == "0") {
        return 0;
    }
    bool negative = (ns.at(0) == '-');
    if (negative) {
        ns.erase(ns.begin());
    }
    int64_t num = 0;
    int64_t power = 1;
    for (auto it = ns.crbegin(); it != ns.crend(); it = std::next(it)) {
        if (getPos( * it) >= absBase) {
            return -1;
        }
        num += getPos( * it) * power;
        power = power * b;
    }
    if (negative) num = -num;
    return num;
}
std::string encodeD(double n, int b) {
    return encode( makeInt(n), b);
}

std::string encodeF(float n, int b) {
    return encode( makeInt(n), b);
}
double decodeD(const std::string & ns, int b) {
    int64_t n = decode(ns, b);
    return makeDF(n, 8);

}
float decodeF(const std::string & ns, int b) {
    int64_t n = decode(ns, b);
    return makeDF(n, 4);
}
int main() {
    using namespace std;
    vector < pair < int64_t, int >> nbl({
        make_pair(8976, -32),
        make_pair(-120160524121052485, 62)
    });
    for (auto & p : nbl) {
        string ns = encode(p.first, p.second);
        cout << setw(12) << p.first << " encoded in base " << setw(3) << p.second << "=" << ns.c_str() << endl;
        int64_t n = decode(ns, p.second);
        cout << setw(12) << ns.c_str() << " decoded in base " << setw(3) << p.second << "=" << n << endl;
        cout << endl;
    }
    cout << "base16=" << encode(12345, 16) << endl;
    cout << "debase16=" << decode("3039", 16) << endl;

    cout << "base36=" << encode(120160524121052485, 36) << endl;
    cout << "decode base36=" << decode("wv5cmq0voed", 36) << endl;

    cout << "negative base36=" << encode(120160524121052485, -36) << endl;
    cout << "decode negative=" << decode("x56ona15pmd", -36) << endl;


    float f = 12345.12345608900043;
    double d = 12345.12345608900043;
    cout << "encodeF=" << encodeF(f, 62) << endl;
    cout << "encodeD=" << encodeD(d, 62) << endl;

    cout << std::fixed << std::setprecision(15) << decodeF("1hLwLA", 62) << endl;
    cout << std::fixed << std::setprecision(15) << decodeD("5yPxSlKBjRC", 62) << endl;

    return 0;
}