## kerberos-tomcat-camel

"As a developer i want to login to Microsoft SQL Server from a java container using integrated security (kerberos)"

### Docker Locally

Kerberos Server
```
docker run --privileged --rm -v /dev/shm:/dev/shm -e RUN_MODE=kadmin --name=kdc-server-kadmin --net host quay.io/eformat/kdc-server:latest
docker run --privileged --rm -v /dev/shm:/dev/shm -e RUN_MODE=kdc --name=kdc-server-kdc --net host quay.io/eformat/kdc-server:latest
```

Add `example.com` domain and host entries to `/etc/hosts`
```
127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4 EXAMPLE.COM kerberos.example.com
```

Adjust `krb5.conf` so we resolve locally
```
[realms]
EXAMPLE.COM = {
    kdc = kerberos.example.com:8888
    admin_server = kerberos.example.com:8749
    kpasswd_server = kerberos.example.com:8464
}
```

Clone this repo and build
```
cd ~/git/ibmmq-tomcat-camel
./buildrun.sh
```

### OpenShift

Login to OpenShift

Deploy example
```
make demo
```

Test
```
http $(oc get route test-example-app --template='{{ .spec.host }}')/camel/hello?name=kerberos
```

Tomcat Logs
```
2019-11-22 04:56:42,213 [main] INFO  org.apache.catalina.startup.Catalina- Server startup in 3473 ms
Hello from KerberosProcessor
>>>KinitOptions cache name is /dev/shm/ccache
>>>DEBUG <CCacheInputStream>  client principal is MSSQLSvc/msql.example.com:1433@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> server principal is krbtgt/EXAMPLE.COM@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> key type: 18
>>>DEBUG <CCacheInputStream> auth time: Fri Nov 22 07:06:06 UTC 2019
>>>DEBUG <CCacheInputStream> start time: Fri Nov 22 07:06:06 UTC 2019
>>>DEBUG <CCacheInputStream> end time: Fri Nov 22 19:06:06 UTC 2019
>>>DEBUG <CCacheInputStream> renew_till time: Sat Nov 23 07:06:06 UTC 2019
>>> CCacheInputStream: readFlags()  RENEWABLE; INITIAL; PRE_AUTH;
>>>DEBUG <CCacheInputStream>  client principal is MSSQLSvc/msql.example.com:1433@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> server principal is X-CACHECONF:/krb5_ccache_conf_data/fast_avail/krbtgt/EXAMPLE.COM@EXAMPLE.COM@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> key type: 0
>>>DEBUG <CCacheInputStream> auth time: Thu Jan 01 00:00:00 UTC 1970
>>>DEBUG <CCacheInputStream> start time: null
>>>DEBUG <CCacheInputStream> end time: Thu Jan 01 00:00:00 UTC 1970
>>>DEBUG <CCacheInputStream> renew_till time: null
>>> CCacheInputStream: readFlags() 
>>>DEBUG <CCacheInputStream>  client principal is MSSQLSvc/msql.example.com:1433@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> server principal is X-CACHECONF:/krb5_ccache_conf_data/pa_type/krbtgt/EXAMPLE.COM@EXAMPLE.COM@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> key type: 0
>>>DEBUG <CCacheInputStream> auth time: Thu Jan 01 00:00:00 UTC 1970
>>>DEBUG <CCacheInputStream> start time: null
>>>DEBUG <CCacheInputStream> end time: Thu Jan 01 00:00:00 UTC 1970
>>>DEBUG <CCacheInputStream> renew_till time: null
>>> CCacheInputStream: readFlags() 
Found ticket for MSSQLSvc/msql.example.com:1433@EXAMPLE.COM to go to krbtgt/EXAMPLE.COM@EXAMPLE.COM expiring on Fri Nov 22 19:06:06 UTC 2019
Entered Krb5Context.initSecContext with state=STATE_NEW
Found ticket for MSSQLSvc/msql.example.com:1433@EXAMPLE.COM to go to krbtgt/EXAMPLE.COM@EXAMPLE.COM expiring on Fri Nov 22 19:06:06 UTC 2019
Service ticket not found in the subject
>>> Credentials acquireServiceCreds: same realm
default etypes for default_tgs_enctypes: 18 17 23.
>>> CksumType: sun.security.krb5.internal.crypto.RsaMd5CksumType
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
>>> KrbKdcReq send: kdc=test TCP:88, timeout=30000, number of retries =3, #bytes=726
>>> KDCCommunication: kdc=test TCP:88, timeout=30000,Attempt =1, #bytes=726
>>>DEBUG: TCPClient reading 725 bytes
>>> KrbKdcReq send: #bytes read=725
>>> KdcAccessibility: remove test:88
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
>>> KrbApReq: APOptions are 00100000 00000000 00000000 00000000
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
Krb5Context setting mySeqNumber to: 193154808
Created InitSecContextToken:
0000: 01 00 6E 82 02 82 30 82   02 7E A0 03 02 01 05 A1  ..n...0.........
0010: 03 02 01 0E A2 07 03 05   00 20 00 00 00 A3 82 01  ......... ......
0020: 7C 61 82 01 78 30 82 01   74 A0 03 02 01 05 A1 0D  .a..x0..t.......
0030: 1B 0B 45 58 41 4D 50 4C   45 2E 43 4F 4D A2 2C 30  ..EXAMPLE.COM.,0
0040: 2A A0 03 02 01 00 A1 23   30 21 1B 08 4D 53 53 51  *......#0!..MSSQ
0050: 4C 53 76 63 1B 15 6D 73   71 6C 2E 65 78 61 6D 70  LSvc..msql.examp
0060: 6C 65 2E 63 6F 6D 3A 31   34 33 33 A3 82 01 2E 30  le.com:1433....0
0070: 82 01 2A A0 03 02 01 12   A1 03 02 01 02 A2 82 01  ..*.............
0080: 1C 04 82 01 18 FA 70 3A   D4 9C F8 62 CA A4 2C F2  ......p:...b..,.
0090: 6E 99 DA DA DE FC DF C1   22 A4 4B B7 03 30 B1 5F  n.......".K..0._
00A0: 61 EA 95 6B 10 DA DF 40   C7 E0 D1 36 7D A2 08 A7  a..k...@...6....
00B0: 37 14 41 8C 2B 64 A1 C5   4D 4B 5D AD 41 A5 51 00  7.A.+d..MK].A.Q.
00C0: F2 B6 0B 6D 92 BC 1F A9   51 06 FB A3 E1 34 AB D0  ...m....Q....4..
00D0: BD A5 45 4D 9B AC 60 84   33 52 D3 54 E9 B8 A3 E9  ..EM..`.3R.T....
00E0: 63 4C CF 2B CC CA 96 87   50 CE 51 A9 4E F9 7F CA  cL.+....P.Q.N...
00F0: 44 D7 84 6A FB 2A DA C3   2D 98 B8 BE 2B C9 87 33  D..j.*..-...+..3
0100: 74 34 8E 29 9F 87 C4 FB   7D CB A3 43 CB 58 2A 56  t4.).......C.X*V
0110: 22 73 AC 3B 37 66 48 9A   3B A2 56 A0 96 89 D6 91  "s.;7fH.;.V.....
0120: F1 BA 39 C5 F0 C5 E6 4D   5F EE EF 57 2C 3A 6E EC  ..9....M_..W,:n.
0130: C3 CD 0B A2 31 04 DA A2   A3 77 FA E8 1E BF 2D EF  ....1....w....-.
0140: BC AB 06 E3 33 2C C7 2B   52 93 33 64 E2 05 41 5D  ....3,.+R.3d..A]
0150: 83 69 AC 93 36 E4 15 74   07 DC 79 BB 02 A8 9C 2C  .i..6..t..y....,
0160: 58 F4 70 AA 7D 4A A2 01   FA 44 7F 58 56 7B 51 ED  X.p..J...D.XV.Q.
0170: AD 35 B3 B4 D8 26 AC 98   B3 2B B3 8F 34 74 11 E5  .5...&...+..4t..
0180: 22 2C 76 BF 11 8F C1 08   FC AF 38 12 DF 46 6B 2E  ",v.......8..Fk.
0190: AE 57 12 72 ED 85 08 26   18 4A 2D 57 5A A4 81 E8  .W.r...&.J-WZ...
01A0: 30 81 E5 A0 03 02 01 12   A2 81 DD 04 81 DA AE 9D  0...............
01B0: D5 CD E1 5B 86 FE 7B 80   9F DC 1B 64 39 DB 33 D4  ...[.......d9.3.
01C0: 3F CC 56 5B 4F BD CF 04   BB 07 A3 87 3A 74 E4 1E  ?.V[O.......:t..
01D0: AA 70 90 51 4F BC 60 1D   25 94 AC 7B 10 2E 62 E8  .p.QO.`.%.....b.
01E0: D1 86 2A D8 70 64 05 96   6C BD 7D 0F 35 9D AD 4E  ..*.pd..l...5..N
01F0: C5 A5 D0 88 8B ED 26 85   A9 3A 9B A0 39 2C C9 12  ......&..:..9,..
0200: AB 00 B4 C3 FB 25 5E B9   ED 90 F8 09 04 9E 2C 27  .....%^.......,'
0210: 1E 5C 8A 1E AF 1D C4 89   1E BA 80 BE 3E C8 8B 23  .\..........>..#
0220: 1C 99 C0 B6 ED B7 7D E4   A1 29 25 41 B5 F9 56 19  .........)%A..V.
0230: 03 AE F4 B7 71 16 C9 B8   A7 B2 D9 73 30 D3 68 ED  ....q......s0.h.
0240: A6 91 88 2E 07 7E 92 13   0B B4 83 E1 5E F9 31 E2  ............^.1.
0250: 3B AC 3C 49 68 53 F7 E1   70 22 5B E5 04 6A 77 B5  ;.<IhS..p"[..jw.
0260: E7 C2 41 C1 02 CB D0 32   33 D5 45 39 F9 81 A6 42  ..A....23.E9...B
0270: 93 93 F6 80 3F 32 2C 69   82 FF 4D C5 43 B6 E9 50  ....?2,i..M.C..P
0280: EC 9E 79 59 F6 87 98 4E                            ..yY...N

Service ticket generated, JAVA works!
Hello from KerberosProcessor
>>>KinitOptions cache name is /dev/shm/ccache
>>>DEBUG <CCacheInputStream>  client principal is MSSQLSvc/msql.example.com:1433@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> server principal is krbtgt/EXAMPLE.COM@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> key type: 18
>>>DEBUG <CCacheInputStream> auth time: Fri Nov 22 07:06:06 UTC 2019
>>>DEBUG <CCacheInputStream> start time: Fri Nov 22 07:06:06 UTC 2019
>>>DEBUG <CCacheInputStream> end time: Fri Nov 22 19:06:06 UTC 2019
>>>DEBUG <CCacheInputStream> renew_till time: Sat Nov 23 07:06:06 UTC 2019
>>> CCacheInputStream: readFlags()  RENEWABLE; INITIAL; PRE_AUTH;
>>>DEBUG <CCacheInputStream>  client principal is MSSQLSvc/msql.example.com:1433@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> server principal is X-CACHECONF:/krb5_ccache_conf_data/fast_avail/krbtgt/EXAMPLE.COM@EXAMPLE.COM@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> key type: 0
>>>DEBUG <CCacheInputStream> auth time: Thu Jan 01 00:00:00 UTC 1970
>>>DEBUG <CCacheInputStream> start time: null
>>>DEBUG <CCacheInputStream> end time: Thu Jan 01 00:00:00 UTC 1970
>>>DEBUG <CCacheInputStream> renew_till time: null
>>> CCacheInputStream: readFlags() 
>>>DEBUG <CCacheInputStream>  client principal is MSSQLSvc/msql.example.com:1433@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> server principal is X-CACHECONF:/krb5_ccache_conf_data/pa_type/krbtgt/EXAMPLE.COM@EXAMPLE.COM@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> key type: 0
>>>DEBUG <CCacheInputStream> auth time: Thu Jan 01 00:00:00 UTC 1970
>>>DEBUG <CCacheInputStream> start time: null
>>>DEBUG <CCacheInputStream> end time: Thu Jan 01 00:00:00 UTC 1970
>>>DEBUG <CCacheInputStream> renew_till time: null
>>> CCacheInputStream: readFlags() 
Found ticket for MSSQLSvc/msql.example.com:1433@EXAMPLE.COM to go to krbtgt/EXAMPLE.COM@EXAMPLE.COM expiring on Fri Nov 22 19:06:06 UTC 2019
Entered Krb5Context.initSecContext with state=STATE_NEW
Found ticket for MSSQLSvc/msql.example.com:1433@EXAMPLE.COM to go to krbtgt/EXAMPLE.COM@EXAMPLE.COM expiring on Fri Nov 22 19:06:06 UTC 2019
Service ticket not found in the subject
>>> Credentials acquireServiceCreds: same realm
default etypes for default_tgs_enctypes: 18 17 23.
>>> CksumType: sun.security.krb5.internal.crypto.RsaMd5CksumType
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
>>> KrbKdcReq send: kdc=test TCP:88, timeout=30000, number of retries =3, #bytes=726
>>> KDCCommunication: kdc=test TCP:88, timeout=30000,Attempt =1, #bytes=726
>>>DEBUG: TCPClient reading 725 bytes
>>> KrbKdcReq send: #bytes read=725
>>> KdcAccessibility: remove test:88
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
>>> KrbApReq: APOptions are 00100000 00000000 00000000 00000000
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
Krb5Context setting mySeqNumber to: 6793218
Created InitSecContextToken:
0000: 01 00 6E 82 02 80 30 82   02 7C A0 03 02 01 05 A1  ..n...0.........
0010: 03 02 01 0E A2 07 03 05   00 20 00 00 00 A3 82 01  ......... ......
0020: 7C 61 82 01 78 30 82 01   74 A0 03 02 01 05 A1 0D  .a..x0..t.......
0030: 1B 0B 45 58 41 4D 50 4C   45 2E 43 4F 4D A2 2C 30  ..EXAMPLE.COM.,0
0040: 2A A0 03 02 01 00 A1 23   30 21 1B 08 4D 53 53 51  *......#0!..MSSQ
0050: 4C 53 76 63 1B 15 6D 73   71 6C 2E 65 78 61 6D 70  LSvc..msql.examp
0060: 6C 65 2E 63 6F 6D 3A 31   34 33 33 A3 82 01 2E 30  le.com:1433....0
0070: 82 01 2A A0 03 02 01 12   A1 03 02 01 02 A2 82 01  ..*.............
0080: 1C 04 82 01 18 3E D6 BE   48 C8 87 DC E0 59 EA 8B  .....>..H....Y..
0090: D8 86 95 58 FC 9F 37 39   69 74 75 16 D3 1E 23 5A  ...X..79itu...#Z
00A0: C5 39 86 F9 5F 02 08 CC   56 26 59 C7 40 23 9D 7F  .9.._...V&Y.@#..
00B0: 74 87 46 26 84 C6 CC FC   9B AB B0 F3 DD DA A1 58  t.F&...........X
00C0: 2E D4 68 CF 24 C5 AD 9A   41 56 CD 70 AC D9 4B 08  ..h.$...AV.p..K.
00D0: 94 FD 98 77 F1 DB 70 90   32 5D 6C BB 24 C9 7A EC  ...w..p.2]l.$.z.
00E0: 5B 0F DF 80 3F C5 99 50   53 3B 4C E4 02 FA A8 93  [...?..PS;L.....
00F0: 5E 3D 0D 03 4A 9D 31 3E   43 4F F6 DA EF 59 A6 FE  ^=..J.1>CO...Y..
0100: C3 9D 44 F9 12 6B 44 70   78 F2 38 93 D5 BD B1 5E  ..D..kDpx.8....^
0110: F1 89 6B 0F 51 A0 1A 98   8C A0 0C 44 D8 FA 69 54  ..k.Q......D..iT
0120: BA 5E 5E D8 6C 6E 30 DB   B1 C7 A5 81 50 FB 80 A2  .^^.ln0.....P...
0130: C4 91 83 6C EB DF D2 3F   E6 38 F1 7A D6 E5 1D 41  ...l...?.8.z...A
0140: FF 09 D4 2A 69 B9 78 B2   DB DA 3A A3 1E 3C 5D 41  ...*i.x...:..<]A
0150: 15 E2 68 BE 21 E3 68 4A   36 F7 67 CD 28 34 62 9C  ..h.!.hJ6.g.(4b.
0160: B8 F2 30 23 23 8C BD 95   9F 00 A4 87 B7 B8 87 79  ..0##..........y
0170: AB 0B 56 A2 A2 58 71 BB   6E 56 C1 51 FB 30 46 30  ..V..Xq.nV.Q.0F0
0180: 2C 19 22 B1 05 AD 45 C9   6D 04 46 FC 77 FE 7A 56  ,."...E.m.F.w.zV
0190: 7A E5 38 4E 37 4F 36 6C   22 E7 A4 54 98 A4 81 E6  z.8N7O6l"..T....
01A0: 30 81 E3 A0 03 02 01 12   A2 81 DB 04 81 D8 83 AB  0...............
01B0: F3 1E 8A 9F 93 98 7A FB   36 73 0E CB 91 32 34 2B  ......z.6s...24+
01C0: 00 E7 3C B7 9C 29 BA 56   33 93 DB BF 37 29 97 02  ..<..).V3...7)..
01D0: CF AC 17 16 77 9E C9 FE   46 04 E4 BD 17 D5 DF 1E  ....w...F.......
01E0: 2B 22 AA 43 A9 41 8F 33   FD 81 D4 1B 3E 00 B6 2A  +".C.A.3....>..*
01F0: 1C 4A 9E 2F BA 86 D4 DA   C0 18 F9 E7 8D B9 5D 20  .J./..........] 
0200: F9 F5 D7 E8 A1 46 59 D3   79 32 47 23 F0 FF 5B 27  .....FY.y2G#..['
0210: 36 9B 68 AA 3D 50 FC 2B   C3 14 62 4A 48 52 11 66  6.h.=P.+..bJHR.f
0220: 9B 97 E8 69 07 AC A3 68   B6 F2 29 4A 97 BD 78 72  ...i...h..)J..xr
0230: 3C 34 B2 43 F5 5F 01 44   91 85 B0 72 F3 AC 30 04  <4.C._.D...r..0.
0240: 92 21 61 4C CD 26 85 1E   59 0A 08 46 FF 55 86 60  .!aL.&..Y..F.U.`
0250: 1F C5 04 0E F7 5A C2 CB   59 07 7C 33 8B 33 8A 92  .....Z..Y..3.3..
0260: 30 6F 3B 12 31 B7 F9 A7   C7 77 DB 5D 39 C5 3A 2D  0o;.1....w.]9.:-
0270: DA 79 1C D4 11 F2 98 64   89 8D 92 B4 68 A3 14 FB  .y.....d....h...
0280: D6 1E 4F 69 FC 60                                  ..Oi.`

Service ticket generated, JAVA works!

```
