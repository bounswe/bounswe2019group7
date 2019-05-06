import React, { Component } from 'react';
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  Col,
  Row,
  ListGroup,
  ListGroupItem,
  Form,
  FormGroup,
  FormText,
  Input,
  Label,
} from 'reactstrap';

const axios = require('axios')
const baseUrl = "http://127.0.0.1:8000/"

class Articles extends Component {
  constructor(props) {
    super(props);

    this.state = {
      articles : [],
    };
  }

  componentDidMount = () => {
    axios.get(baseUrl+'articles/').then((response)=>
      {this.setState({articles : response.data})})
  }

  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          {this.state.articles.map((value, index)=>{
            return(
          <Col sm="6" md="4" xl="3" key={index}>
            <Card>
                <CardHeader>
                    <i className="fa fa-certificate"></i><strong>{value["article_title"]}</strong>
                </CardHeader>
                <CardBody>
                  {value["article_text"]}
                </CardBody>
            </Card>
          </Col>);
          })}
        </Row>

      </div>
    );
  }
}

export default Articles;
