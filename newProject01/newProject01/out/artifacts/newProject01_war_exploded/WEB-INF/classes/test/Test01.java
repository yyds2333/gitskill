import com.powernode.dao.BaseDao;
import com.powernode.dao.impl.BaseDaoImpl;
import com.powernode.domain.Student;
import com.powernode.service.impl.StudentServiceImpl;
import com.powernode.utils.PageInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test01 {
    BaseDao bd = new BaseDaoImpl();

    // 通过
    @Test
    public void testdeleteAll() {
        int[] ints = bd.deleteAll("delete from product_tbl where id = ?", 23, 24, 26, 25);
        System.out.println(Arrays.toString(ints));
    }
    //通过
    @Test
    public void testUpdate() {
        Object[] obj = {1000985,"洛丽塔","女","asd123456",5};
        int update = bd.update("insert into student_tbl values(null,?,?,?,?,?)", obj);
        System.out.println(update);
    }
    //通过
    @Test
    public void testSelectOne() {
//        Student student = bd.selectOne("select * from student_tbl where stuId = ?",Student.class,100565);
        Student student = new StudentServiceImpl().selectOne("1");
        System.out.println(student.toString());
    }
    //通过
    @Test
    public void testSelectAll() {
        List<Student> students = new ArrayList<>();
        students = bd.selectList("select * from student_tbl", Student.class);
        students.forEach(System.out::println);
    }
    //通过
    @Test
    public void testSelectPage() {
        List<Student> students = null;
        PageInfo<Student> pa = bd.selectListPage("select * from student_tbl where gender = ? ", 1, 10, Student.class, "女");
        students = pa.getList();
        students.forEach(System.out::println);
        System.out.println("查询页面容量="+pa.getPageSize());
        System.out.println("查询第"+pa.getPagaColumn()+"页");
        System.out.println("查询总条数="+pa.getTotalNums());
        System.out.println("查询总页数="+pa.getTotalCount());
    }




    //通过
    @Test
    public void others() {
        String sql = "select * from pro where id = ?  ;";
        String s1 = sql.replaceFirst(";", "");
        System.out.println(s1);
        String s = s1 + " limit " + 2+","+5;
        System.out.println(s);
    }

    @Test
    public void setBd() {
        String ids = "1,5,6,4,8,2,63,5";
        String[] idss = ids.split(",");
        for (String s:idss) {
            System.out.println(s);
        }
    }

    @Test
    public void testSelectOne1(){
        System.out.println();
//        long id = Long.valueOf("stuid");
//        System.out.println(id);
        //        tests(Long.valueOf(100025));
    }

    public void tests(Object id){
        String s = String.valueOf(id);
        System.out.println(s.startsWith("100"));
        System.out.println(s.length());
    }
}
