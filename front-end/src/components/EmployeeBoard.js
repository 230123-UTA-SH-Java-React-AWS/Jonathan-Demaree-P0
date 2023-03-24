import React, { useState, useEffect } from "react";

import User from "./../User";

const EmployeeBoard = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    User.getEmployeeBoard().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setContent(_content);
      }
    );
  }, []);

  return (
    <div className="container">
      <header className="jumbotron">
        <h3>{content}</h3>
      </header>
    </div>
  );
};

export default EmployeeBoard;