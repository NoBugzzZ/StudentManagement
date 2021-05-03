#### 查询所有学生信息
url: `/students`  
method: `GET`

#### 查询单个学生信息
url: `/students/{id}`  
method: `GET`

#### 添加学生信息
url: `/students`  
method: `POST`  
说明: 请求体内需要包含json格式的数据，示例如下  
```
{
    "studentId": "MF20330073",
    "name": "Tangzheng",
    "sex": "male",
    "birthday": "1998-05-01",
    "nativePlace": "Jiangsu",
    "department": "computer"
}
```

#### 修改学生信息
url: `/students/{id}`  
method: `PUT`  
说明: 请求体内需要和添加学生信息相同的数据格式  

#### 删除学生信息
url: `/students/{id}`  
method: `DELETE`  