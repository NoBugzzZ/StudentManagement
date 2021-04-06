## 实验环境
- Apache Maven 3.6.3
- jdk1.8.0_281
- Docker version 20.10.5
- macos 11.2.3

## 实验操作
1. 在数据库中添加1000条数据
2. 使用haproxy水平扩展
3. 实现service类，用于实现cache技术
4. 使用gatling进行项目压力测试

## 实验结果
本实验使用内存数据库，不使用cache的响应速度与使用的相应速度相差无几。不过总体上还是随着水平节点的增多，相应速度也就相应的变快。
|  1个节点，无cache   | 1个节点，有cache  |2个节点，无cache|2个节点，有cache|4个节点，无cache|4个节点，有cache|
|  ----  | ----  |----  |----  |----  |----  |
| 2491  | 2940 |1269 |1759 |1122 |1181 |
| 2206  | 2054 |1427 |1504 |1126 |1290 |
| 2416  | 2044 |1395 |1548 |1176 |1167 |
| 1741  | 1672 |1382 |1450 |1090 |1044 |
| 2194  | 1926 |1286 |1358 |1125 |1283 |
| 1544  | 1782 |1251 |1293 |1022 |1171 | 

## 实验展望
后面可以使用mysql数据库，实验预期应该会是使用cache技术的响应时间更短。