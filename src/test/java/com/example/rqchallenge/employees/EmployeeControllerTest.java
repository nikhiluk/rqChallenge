package com.example.rqchallenge.employees;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 9090)
class EmployeeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    public void setup() {
        stubFor(get(urlEqualTo("/api/v1/employees"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_name\":\"Tiger Nixon\",\"employee_salary\":320800,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":2,\"employee_name\":\"Garrett Winters\",\"employee_salary\":170750,\"employee_age\":63,\"profile_image\":\"\"},{\"id\":3,\"employee_name\":\"Ashton Cox\",\"employee_salary\":86000,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":4,\"employee_name\":\"Cedric Kelly\",\"employee_salary\":433060,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":5,\"employee_name\":\"Airi Satou\",\"employee_salary\":162700,\"employee_age\":33,\"profile_image\":\"\"},{\"id\":6,\"employee_name\":\"Brielle Williamson\",\"employee_salary\":372000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":7,\"employee_name\":\"Herrod Chandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":8,\"employee_name\":\"Rhona Davidson\",\"employee_salary\":327900,\"employee_age\":55,\"profile_image\":\"\"},{\"id\":9,\"employee_name\":\"Colleen Hurst\",\"employee_salary\":205500,\"employee_age\":39,\"profile_image\":\"\"},{\"id\":10,\"employee_name\":\"Sonya Frost\",\"employee_salary\":103600,\"employee_age\":23,\"profile_image\":\"\"},{\"id\":11,\"employee_name\":\"Jena Gaines\",\"employee_salary\":90560,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":12,\"employee_name\":\"Quinn Flynn\",\"employee_salary\":342000,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":13,\"employee_name\":\"Charde Marshall\",\"employee_salary\":470600,\"employee_age\":36,\"profile_image\":\"\"},{\"id\":14,\"employee_name\":\"Haley Kennedy\",\"employee_salary\":313500,\"employee_age\":43,\"profile_image\":\"\"},{\"id\":15,\"employee_name\":\"Tatyana Fitzpatrick\",\"employee_salary\":385750,\"employee_age\":19,\"profile_image\":\"\"},{\"id\":16,\"employee_name\":\"Michael Silva\",\"employee_salary\":198500,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":17,\"employee_name\":\"Paul Byrd\",\"employee_salary\":725000,\"employee_age\":64,\"profile_image\":\"\"},{\"id\":18,\"employee_name\":\"Gloria Little\",\"employee_salary\":237500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":19,\"employee_name\":\"Bradley Greer\",\"employee_salary\":132000,\"employee_age\":41,\"profile_image\":\"\"},{\"id\":20,\"employee_name\":\"Dai Rios\",\"employee_salary\":217500,\"employee_age\":35,\"profile_image\":\"\"},{\"id\":21,\"employee_name\":\"Jenette Caldwell\",\"employee_salary\":345000,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":22,\"employee_name\":\"Yuri Berry\",\"employee_salary\":675000,\"employee_age\":40,\"profile_image\":\"\"},{\"id\":23,\"employee_name\":\"Caesar Vance\",\"employee_salary\":106450,\"employee_age\":21,\"profile_image\":\"\"},{\"id\":24,\"employee_name\":\"Doris Wilder\",\"employee_salary\":85600,\"employee_age\":23,\"profile_image\":\"\"}],\"message\":\"Successfully! All records has been fetched.\"}")));
        stubFor(get(urlEqualTo("/api/v1/employee/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("{\"status\":\"success\",\"data\":{\"id\":1,\"employee_name\":\"Tiger Nixon\",\"employee_salary\":320800,\"employee_age\":61,\"profile_image\":\"\"},\"message\":\"Successfully! Record has been fetched.\"}")));

    }

    @Test
    public void getAllEmployees() {
        ResponseEntity<List<Employee>> response = restTemplate.exchange("http://localhost:" + port,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        final int statusCodeValue = response.getStatusCodeValue();

        assertEquals(200, statusCodeValue);
        final List<Employee> body = response.getBody();
        assertNotNull(body);
        assertEquals(24, body.size());
    }

    @Test
    public void getEmployeeById() {
        ResponseEntity<Employee> response = restTemplate.exchange("http://localhost:" + port + "/1",
                HttpMethod.GET,
                null,
                Employee.class);

        final int statusCodeValue = response.getStatusCodeValue();

        assertEquals(200, statusCodeValue);
        final Employee employee = response.getBody();
        assertNotNull(employee);
        assertEquals(1, employee.getId());
        assertEquals("Tiger Nixon", employee.getEmployeeName());
    }

    @Test
    public void getEmployeesByNameSearchExactMatch() throws UnsupportedEncodingException {
        final String encoded = URLEncoder.encode("Tiger Nixon", StandardCharsets.UTF_8.toString());
        ResponseEntity<List<Employee>> response = restTemplate.exchange("http://localhost:" + port + "/search/" + encoded,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        final int statusCodeValue = response.getStatusCodeValue();

        assertEquals(200, statusCodeValue);
        final List<Employee> employees = response.getBody();
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(1, employees.get(0).getId());
        assertEquals("Tiger Nixon", employees.get(0).getEmployeeName());
    }

    @Test
    public void getEmployeesByNameSearchContains() throws UnsupportedEncodingException {
        final String encoded = URLEncoder.encode("Tiger", StandardCharsets.UTF_8.toString());
        ResponseEntity<List<Employee>> response = restTemplate.exchange("http://localhost:" + port + "/search/" + encoded,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        final int statusCodeValue = response.getStatusCodeValue();

        assertEquals(200, statusCodeValue);
        final List<Employee> employees = response.getBody();
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(1, employees.get(0).getId());
        assertEquals("Tiger Nixon", employees.get(0).getEmployeeName());
    }

    @Test
    public void getHighestEarningEmployee() {
        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/highestSalary",
                HttpMethod.GET,
                null,
                Integer.class);

        final int statusCodeValue = response.getStatusCodeValue();

        assertEquals(200, statusCodeValue);
        assertNotNull(response);
        assertEquals(725000, response.getBody());
    }

}