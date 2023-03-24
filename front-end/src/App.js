import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import Auth from "./Auth";

import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";
import EmployeeBoard from "./components/EmployeeBoard";
import ManagerBoard from "./components/ManagerBoard";

const App = () => {
  const [showManagerPages, setShowManagerPages] = useState(false);
  const [showEmployeePages, setShowEmployeePages] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = Auth.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setShowEmployeePages(user.roles.includes("ROLE_EMPLOYEE"));
      setShowManagerPages(user.roles.includes("ROLE_MANAGER"));
    }
  }, []);

  const logOut = () => {
    Auth.logout();
    setShowManagerPages(false);
    setShowEmployeePages(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/"} className="navbar-brand">
          Revature Ticketing Service
        </Link>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">
              Home
            </Link>
          </li>

          {showManagerPages && (
            <li className="nav-item">
              <Link to={"/manager"} className="nav-link">Manager Page</Link>
            </li>
          )}

          {showEmployeePages && (
            <li className="nav-item">
              <Link to={"/employee"} className="nav-link">Employee Page</Link>
            </li>
          )}
        </div>

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <a href="/register" className="nav-link" onClick={logOut}>
                LogOut
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign Up
              </Link>
            </li>
          </div>
        )}
      </nav>

      <div className="container mt-3">
        <Routes>
          <Route exact path={"/"} element={<Home />} />
          <Route exact path={"/home"} element={<Home />} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          <Route path="/employee" element={<EmployeeBoard />} />
          <Route path="/manager" element={<ManagerBoard />} />
        </Routes>
      </div>
    </div>
  );
};

export default App;
