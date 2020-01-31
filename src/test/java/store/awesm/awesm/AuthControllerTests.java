package store.awesm.awesm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import store.awesm.domain.vo.ResultCode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

//  ############ /api/auth/register ##############
    @Test
    @Transactional
    void testRegisterSuccess() throws Exception {
        JSONObject param = new JSONObject();
        param.put("username", "Test2");
        param.put("email", "test@test2.com");
        param.put("password", "password");
        this.mockMvc.perform(
                post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andExpect(jsonPath("$.data.jwt").isNotEmpty())
                .andReturn();
    }

    @Test
    @Transactional
    void testRegisterFailByMissUsername() throws Exception {
        JSONObject param = new JSONObject();
        param.put("email", "test@test2.com");
        param.put("password", "password");
        this.mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(800))
                .andExpect(jsonPath("$.msg").value("register failed"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }

    @Test
    @Transactional
    void testRegisterFailByMissEmail() throws Exception {
        JSONObject param = new JSONObject();
        param.put("username", "Test2");
        param.put("password", "password");
        this.mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(800))
                .andExpect(jsonPath("$.msg").value("register failed"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }


    @Test
    @Transactional
    void testRegisterFailByMissPassword() throws Exception {
        JSONObject param = new JSONObject();
        param.put("username", "Test2");
        param.put("email", "test@test2.com");
        this.mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(800))
                .andExpect(jsonPath("$.msg").value("register failed"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }


    @Test
    @Transactional
    void testRegisterFailByMissAll() throws Exception {
        JSONObject param = new JSONObject();
        this.mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(800))
                .andExpect(jsonPath("$.msg").value("register failed"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }


//  ############ /api/auth/login ##############
    @Test
     void testLoginSuccessWithUsername() throws Exception {
        JSONObject param = new JSONObject();
        param.put("username", "T2est");
        param.put("password", "password");
        this.mockMvc.perform(
                post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andExpect(jsonPath("$.data.jwt").isNotEmpty())
                .andReturn();
    }

    @Test
     void testLoginSuccessWithEmail() throws Exception {
        JSONObject param = new JSONObject();
        param.put("username", "test@test.com");
        param.put("password", "password");
        this.mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andExpect(jsonPath("$.data.jwt").isNotEmpty())
                .andReturn();
    }

    @Test
    void testLoginFailByWrongPassword() throws Exception {
        JSONObject param = new JSONObject();
        param.put("username", "T2est");
        param.put("password", "password2");
        this.mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.msg").value("Bad credentials"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }

    @Test
     void testLoginFailByMissUsernameAndEmail() throws Exception {
        JSONObject param = new JSONObject();
        param.put("password", "password");
        this.mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.msg").value("Bad credentials"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }

    @Test
     void testLoginFailWithUsernameByMissPassword() throws Exception {
        JSONObject param = new JSONObject();
        param.put("username", "T2est");
        this.mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.msg").value("Bad credentials"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }

    @Test
     void testLoginFailWithEmailByMissPassword() throws Exception {
        JSONObject param = new JSONObject();
        param.put("username", "test@test2.com");
        this.mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.msg").value("Bad credentials"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }

//  ############ /api/auth/forgot-password ##############
    @Test
    void testForgotPasswordBeforeVerify() throws Exception  {
        JSONObject param = new JSONObject();
        param.put("email", "test@test2.com");
        this.mockMvc.perform(
                post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(299))
                .andExpect(jsonPath("$.msg").value("please get captcha first"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }

//  ############ /api/auth/reset-password ##############
    @Test
    void testResetPasswordBeforeVerify() throws Exception  {
        JSONObject param = new JSONObject();
        param.put("email", "test@test2.com");
        this.mockMvc.perform(
                post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(298))
                .andExpect(jsonPath("$.msg").value("please pass the verification first"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andReturn();
    }
}
