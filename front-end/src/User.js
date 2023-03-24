import axios from "axios";

const API_URL = "http://localhost:8000/ticket/";

const getPublicContent = () => {
    return axios.get(API_URL + "all");
  };

const getPendingTickets = () => {
    return axios.get(API_URL + "pendingTickets");
};

const getUserTickets = () => {
    return axios.get(API_URL + "userTickets");
};

const createTicket = () => {
    return axios.get(API_URL + "newTicket");
};

const processTicket = () => {
    return axios.get(API_URL + "processTicket");
};

const User = {
    getPublicContent,
    getPendingTickets,
    getUserTickets,
    createTicket,
    processTicket,
}

export default User;