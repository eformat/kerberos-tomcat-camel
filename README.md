## kerberos-tomcat-camel

### Locally

Clone this repo and build
```
cd ~/git/ibmmq-tomcat-camel
./buildrun.sh
```

### OpenShift

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
>>>KinitOptions cache name is /tmp/krb5cc_1000
>>>DEBUG <CCacheInputStream>  client principal is MSSQLSvc/msql.example.com:1433@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> server principal is krbtgt/EXAMPLE.COM@EXAMPLE.COM
>>>DEBUG <CCacheInputStream> key type: 18
>>>DEBUG <CCacheInputStream> auth time: Fri Nov 22 02:38:21 UTC 2019
>>>DEBUG <CCacheInputStream> start time: Fri Nov 22 02:38:21 UTC 2019
>>>DEBUG <CCacheInputStream> end time: Fri Nov 22 14:38:21 UTC 2019
>>>DEBUG <CCacheInputStream> renew_till time: Fri Nov 29 02:38:21 UTC 2019
>>> CCacheInputStream: readFlags()  FORWARDABLE; RENEWABLE; INITIAL; PRE_AUTH;
>>>DEBUG <CCacheInputStream>  client principal is MSSQLSvc/msql.example.com:1433@EXAMPLE.COM
Java config name: /opt/webserver/conf/krb5.conf
Loaded from Java config
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
Found ticket for MSSQLSvc/msql.example.com:1433@EXAMPLE.COM to go to krbtgt/EXAMPLE.COM@EXAMPLE.COM expiring on Fri Nov 22 14:38:21 UTC 2019
Entered Krb5Context.initSecContext with state=STATE_NEW
Found ticket for MSSQLSvc/msql.example.com:1433@EXAMPLE.COM to go to krbtgt/EXAMPLE.COM@EXAMPLE.COM expiring on Fri Nov 22 14:38:21 UTC 2019
Service ticket not found in the subject
>>> Credentials acquireServiceCreds: same realm
default etypes for default_tgs_enctypes: 18 17 23.
>>> CksumType: sun.security.krb5.internal.crypto.RsaMd5CksumType
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
>>> KdcAccessibility: reset
>>> KrbKdcReq send: kdc=kerberos.example.com TCP:8888, timeout=30000, number of retries =3, #bytes=726
>>> KDCCommunication: kdc=kerberos.example.com TCP:8888, timeout=30000,Attempt =1, #bytes=726
>>>DEBUG: TCPClient reading 725 bytes
>>> KrbKdcReq send: #bytes read=725
>>> KdcAccessibility: remove kerberos.example.com:8888
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
default etypes for default_tgs_enctypes: 18 17 23.
>>> CksumType: sun.security.krb5.internal.crypto.RsaMd5CksumType
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
>>> KrbKdcReq send: kdc=kerberos.example.com TCP:8888, timeout=30000, number of retries =3, #bytes=714
>>> KDCCommunication: kdc=kerberos.example.com TCP:8888, timeout=30000,Attempt =1, #bytes=714
>>>DEBUG: TCPClient reading 700 bytes
>>> KrbKdcReq send: #bytes read=700
>>> KdcAccessibility: remove kerberos.example.com:8888
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
>>> KrbApReq: APOptions are 00100000 00000000 00000000 00000000
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
Krb5Context setting mySeqNumber to: 342054555
Created InitSecContextToken:
0000: 01 00 6E 82 05 4E 30 82   05 4A A0 03 02 01 05 A1  ..n..N0..J......
0010: 03 02 01 0E A2 07 03 05   00 20 00 00 00 A3 82 01  ......... ......
0020: 7C 61 82 01 78 30 82 01   74 A0 03 02 01 05 A1 0D  .a..x0..t.......
0030: 1B 0B 45 58 41 4D 50 4C   45 2E 43 4F 4D A2 2C 30  ..EXAMPLE.COM.,0
0040: 2A A0 03 02 01 00 A1 23   30 21 1B 08 4D 53 53 51  *......#0!..MSSQ
0050: 4C 53 76 63 1B 15 6D 73   71 6C 2E 65 78 61 6D 70  LSvc..msql.examp
0060: 6C 65 2E 63 6F 6D 3A 31   34 33 33 A3 82 01 2E 30  le.com:1433....0
0070: 82 01 2A A0 03 02 01 12   A1 03 02 01 02 A2 82 01  ..*.............
0080: 1C 04 82 01 18 54 56 A9   BF E6 FC A2 3A F3 F6 A8  .....TV.....:...
0090: FA 59 18 B6 1C 84 D7 41   76 A5 63 27 F5 D5 23 9A  .Y.....Av.c'..#.
00A0: 5C 75 AD 23 50 FB BE 13   9B 2E 41 4C 85 A9 CA 4A  \u.#P.....AL...J
00B0: 74 09 12 CE 57 E5 6C EA   93 0C 7C 3A 70 01 D1 48  t...W.l....:p..H
00C0: 84 FB 74 D8 F4 76 46 9F   62 3F 99 E0 D2 F9 EE 99  ..t..vF.b?......
00D0: FC 87 5C E2 75 02 BF 22   D3 97 5F F0 A9 16 01 22  ..\.u..".._...."
00E0: 5C FE AF DF CA B4 59 C5   7D 8D BB 82 6B 18 B9 4D  \.....Y.....k..M
00F0: 42 81 B7 48 21 BF 15 B9   56 98 DE 27 26 7C 7B 0F  B..H!...V..'&...
0100: 0F 26 02 60 6C 87 54 A4   42 63 44 34 62 E5 49 7E  .&.`l.T.BcD4b.I.
0110: 5A 17 63 C5 4F BB 9F EB   CE EE 6F E2 EF 06 01 C3  Z.c.O.....o.....
0120: 43 CC B2 34 92 E0 61 A2   A5 B1 5F 9D 99 BB 43 BE  C..4..a..._...C.
0130: 9E 7D CB 7B F0 0A D6 4D   DD 8A 22 DD 15 01 10 AA  .......M..".....
0140: 56 A8 07 21 53 4C 9C 00   1D FD AB F1 84 88 B5 5E  V..!SL.........^
0150: BC C8 91 F2 2C 32 EC A5   4E 98 18 1A 3F E2 18 E3  ....,2..N...?...
0160: 7C 06 66 7D 30 59 65 A1   8F 4C E5 58 74 83 6D E5  ..f.0Ye..L.Xt.m.
0170: 46 65 81 D9 CF AF A6 08   08 9E 2D 24 36 D1 85 0B  Fe........-$6...
0180: 0F 48 51 30 CA A2 C6 83   AB D5 EB FF E3 27 BF C6  .HQ0.........'..
0190: 06 F8 55 E8 57 89 5C 1F   53 46 08 F5 E4 A4 82 03  ..U.W.\.SF......
01A0: B3 30 82 03 AF A0 03 02   01 12 A2 82 03 A6 04 82  .0..............
01B0: 03 A2 2A F9 55 7E 49 40   6E 3F 00 8B E4 5D BF D2  ..*.U.I@n?...]..
01C0: 1B 6E 8D 89 32 2B 02 63   A4 F5 8B 78 95 FB 2E E6  .n..2+.c...x....
01D0: C8 50 79 29 AF 14 92 6E   D8 5F 08 7B D6 7D E0 E1  .Py)...n._......
01E0: 31 7F 55 A0 28 9C E0 28   7E A4 C6 22 5A CA 36 BF  1.U.(..(..."Z.6.
01F0: 68 D4 7B 07 4C 69 E4 19   EB E9 D2 0D 20 FB F4 50  h...Li...... ..P
0200: 0B B8 89 9A 90 40 58 55   B5 28 8A 53 F6 60 5D 72  .....@XU.(.S.`]r
0210: D8 28 2A 3D A1 BB D9 7C   CB 92 1F C9 CF E1 70 D3  .(*=..........p.
0220: E7 AD FA DF C8 2F BF 44   83 FE 04 78 2E E6 B3 65  ...../.D...x...e
0230: 93 34 48 6B 5B 2D 67 D5   59 17 EF 55 51 75 F4 41  .4Hk[-g.Y..UQu.A
0240: 87 C7 F2 D4 43 93 97 71   34 AC B5 D4 D1 9C 21 0F  ....C..q4.....!.
0250: 35 AB 5E 90 F6 62 43 3F   02 1F D1 14 51 DD 06 AC  5.^..bC?....Q...
0260: 4D 33 D3 A3 04 1F 7B 0C   58 40 74 84 F0 CA 66 74  M3......X@t...ft
0270: B5 5B 9A 0D 10 36 3A 04   BD D8 48 DA 2C 0C 24 94  .[...6:...H.,.$.
0280: 0A 63 E7 14 B8 6B B6 65   12 77 77 7E FA 4C B5 1F  .c...k.e.ww..L..
0290: 9F 76 DC 40 AF 69 45 7B   10 27 9E A1 C1 53 A1 CE  .v.@.iE..'...S..
02A0: E9 55 0C 6F C5 19 18 B6   44 2E E5 71 7B 23 BC 1C  .U.o....D..q.#..
02B0: 64 09 22 94 46 DB 71 78   42 59 BF CA 45 FD 4F B1  d.".F.qxBY..E.O.
02C0: C2 72 42 FF 28 E4 82 20   C2 F2 09 0B 57 E4 69 99  .rB.(.. ....W.i.
02D0: B1 B7 E6 71 25 BC 3F 9A   BB F6 3B E0 0D BD 9A FC  ...q%.?...;.....
02E0: EA C1 B7 F4 22 BD A0 83   3A 19 9E 14 F9 F7 1C DC  ...."...:.......
02F0: 21 6D 30 44 D2 52 C3 3B   66 28 FE 3D 92 59 61 35  !m0D.R.;f(.=.Ya5
0300: 78 B6 71 F1 94 D2 D1 AB   EB F5 7D 9F 30 05 78 4E  x.q.........0.xN
0310: CE 34 B6 63 30 4C B3 FB   BD E9 A6 53 65 E9 2E 74  .4.c0L.....Se..t
0320: 23 CD A3 9D 99 E0 8B 45   C1 A4 D1 41 9B E4 8B FA  #......E...A....
0330: B5 7B 34 F8 0E 10 01 01   81 34 7E D7 08 B4 FB 4E  ..4......4.....N
0340: AC 1B 35 BB 9D 9E 91 37   0A AD 35 58 AF B4 64 34  ..5....7..5X..d4
0350: 95 56 AC 8C 57 78 77 A9   94 74 90 F2 56 E9 DE 54  .V..Wxw..t..V..T
0360: FC E6 FB 9F 6F 45 53 65   8C DC 21 2C F9 7E 2B 72  ....oESe..!,..+r
0370: 5E 30 05 B2 24 33 6E 86   7A B5 A4 8D 9A 0E 32 4F  ^0..$3n.z.....2O
0380: 82 EA DC 1D F2 F8 FE 3A   0D BF CD F7 1E FF 0F 9B  .......:........
0390: A1 B8 C3 9E BA 7A DC 67   DD 19 BD E6 BD 22 F2 4B  .....z.g.....".K
03A0: F1 AB B8 5E 4C 61 FB 00   01 C8 51 D1 6E E1 F9 35  ...^La....Q.n..5
03B0: 6A 2A EB 55 41 78 F3 3D   96 0E 90 CC 20 8F F9 ED  j*.UAx.=.... ...
03C0: 6D F8 CD 6A 10 8E 2F 4D   5D 20 F3 19 7A 25 37 55  m..j../M] ..z%7U
03D0: 68 71 9F 53 A5 31 B5 A3   EA 7A B8 DA 0E D5 61 C9  hq.S.1...z....a.
03E0: 97 7C 1E A1 94 A2 7F 06   49 A9 96 DB F4 73 53 84  ........I....sS.
03F0: 64 67 CE 31 F0 F2 7C A8   88 7B 7D D5 60 52 43 BF  dg.1........`RC.
0400: 95 2B 3F 21 32 0C 8B 90   DA 21 18 37 66 64 8D E2  .+?!2....!.7fd..
0410: 1A BD 3F 43 A8 BB 79 41   3B 6E 75 78 A7 99 FB A9  ..?C..yA;nux....
0420: 5F 86 70 CB CB 58 DF 6A   2A 07 72 2B BD A6 12 EE  _.p..X.j*.r+....
0430: 74 5C 2D 22 CE D2 49 CE   19 F1 3D 2D 5E DE A7 FF  t\-"..I...=-^...
0440: E5 17 74 1D 57 87 8B 3B   A7 C9 83 53 B6 CC B5 CD  ..t.W..;...S....
0450: 5D DF 5B 6F 8F 71 89 90   DC 06 AC 26 8D 87 A3 0A  ].[o.q.....&....
0460: BA 7A DE 7E 4E 03 02 DD   23 C2 8A 27 15 A2 FE A0  .z..N...#..'....
0470: AB C5 83 E8 E1 D6 EF 32   BA 3E B5 7C 00 43 4B 4D  .......2.>...CKM
0480: 29 38 28 16 52 96 59 77   51 EE EA 99 0E F2 D9 A6  )8(.R.YwQ.......
0490: EB 32 79 08 25 08 EA 35   22 4B 19 70 CE 82 7A F2  .2y.%..5"K.p..z.
04A0: 33 C9 AD 91 F6 96 3E 1C   FF A0 23 52 38 25 04 F9  3.....>...#R8%..
04B0: 49 5E 2F C0 9F 6B E5 3B   16 86 08 72 B6 42 D2 3D  I^/..k.;...r.B.=
04C0: B4 5B 9F 5A F7 71 09 74   BD A2 E9 A5 EC 24 B8 D4  .[.Z.q.t.....$..
04D0: 2C 6B 45 3C AB FC E7 6E   78 16 06 4C 9C 58 41 1C  ,kE<...nx..L.XA.
04E0: A1 9E AF AB 9B 45 C0 59   A4 C3 B6 59 F9 00 8E 2E  .....E.Y...Y....
04F0: 2D 12 3E 98 44 3D 45 C2   89 80 D5 5C 38 52 A2 32  -.>.D=E....\8R.2
0500: C0 0B BC EF CA D9 15 60   DC 16 FC 32 D4 E3 07 5E  .......`...2...^
0510: 65 D5 30 B8 50 E2 29 73   38 60 E7 27 75 AB 8F 3B  e.0.P.)s8`.'u..;
0520: 11 6B E7 BD DC 84 D9 1E   A4 CD 23 11 08 30 0D 11  .k........#..0..
0530: B5 A4 74 73 8A 97 E5 0D   B3 24 14 57 90 15 61 2F  ..ts.....$.W..a/
0540: A7 E8 4A 7A BC F2 42 C7   5E A3 07 82 9A DF A5 FB  ..Jz..B.^.......
0550: F4 33 1F 3F                                        .3.?

Service ticket generated, JAVA works!

```
