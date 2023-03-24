import axios from "axios";

const API_URL = "http://localhost:8000/auth/";

const register = (email, password) => {
    return axios.post(API_URL + "register", {
        email,
        password,
    });
};

const login = (email, password) => {
    return axios
        .post(API_URL + "login", {
            email,
            password,
        })
        .then((response) => {
            if (response.data.username) {
                localStorage.setItem("userInfo", JSON.stringify(response.data));
            }

            return response.data;
        });
};

const logout = () => {
    localStorage.removeItem("user");
        return axios.post(API_URL + "signout").then((response) => {
        return response.data;
    });
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const Auth = {
    register,
    login,
    logout,
    getCurrentUser
}

export default Auth;