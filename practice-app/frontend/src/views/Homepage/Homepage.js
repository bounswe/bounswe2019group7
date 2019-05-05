import React, { Component} from 'react';
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

class HomePage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      quantityDollars: 0,
      quantityTL: 0,
      parities: [],
      convertedDollars: "",
      corvertedTL: ""
    };

  }

  componentDidMount = () => {
    axios.get(baseUrl+'currencies/').then((response)=>
      {this.setState({parities : response.data})})
  }

  handleChange = (e) => {
    this.setState({[e.target.name] : e.target.value})
  }

  convertToDollars = (e) => {
    e.preventDefault();
    this.setState({convertedDollars : this.state.quantityTL*this.state.parities[0]["Realtime Currency Exchange Rate"]["9. Ask Price"]})
  }

  convertToTL = (e) => {
    e.preventDefault();
    this.setState({convertedTL : this.state.quantityDollars*this.state.parities[1]["Realtime Currency Exchange Rate"]["9. Ask Price"]})
  }


  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          <Col sm="12" xl="6">
            <Card>
              <CardHeader>
                <i className="fa fa-align-justify"></i><strong>Currencies</strong>
              </CardHeader>
              <CardBody>
                <ListGroup>
                  {this.state.parities.map((value, index) => {
                    return <ListGroupItem key={index}>{(value["Realtime Currency Exchange Rate"]["1. From_Currency Code"]+"/"+value["Realtime Currency Exchange Rate"]["3. To_Currency Code"]+": "+value["Realtime Currency Exchange Rate"]["9. Ask Price"])}</ListGroupItem>
                  })}
                </ListGroup>
              </CardBody>
            </Card>
          </Col>

          <Col xs="12" md="6">
            <Card>
              <CardHeader>
                <strong>Dollars to Turkish Liras</strong>
              </CardHeader>
              <CardBody>
                <Form onSubmit={this.convertToTL} className="form-horizontal">
                  <FormGroup row>
                    <Col md="2">
                      <Label htmlFor="hf-email">Dollars:</Label>
                    </Col>
                    <Col md="4">
                      <Input placeholder="Enter Quantity" name="quantityDollars" value={this.state.quantityDollars} onChange={this.handleChange} />
                      <FormText className="help-block">Please enter the quantity</FormText>
                    </Col>
                    <Col md="3">
                      <Button type="submit" size="sm" color="primary"><i className="fa fa-dot-circle-o"></i> Convert</Button>
                    </Col>
                    <Col md="3">
                      <h3> {this.state.convertedTL} </h3>
                    </Col>
                  </FormGroup>
                </Form>
              </CardBody>
            </Card>

            <Card>
              <CardHeader>
                <strong>Turkish Liras to Dollars</strong>
              </CardHeader>
              <CardBody>
                <Form onSubmit={this.convertToDollars} className="form-horizontal">
                  <FormGroup row>
                    <Col md="2">
                      <Label htmlFor="hf-email">TL:</Label>
                    </Col>
                    <Col md="4">
                      <Input placeholder="Enter Quantity" name="quantityTL" value={this.state.quantityTL} onChange={this.handleChange} />
                      <FormText className="help-block">Please enter the quantity</FormText>
                    </Col>
                    <Col md="3">
                      <Button type="submit" size="sm" color="primary"><i className="fa fa-dot-circle-o"></i> Convert</Button>
                    </Col>
                    <Col md="3">
                      <h3> {this.state.convertedDollars} </h3>
                    </Col>
                  </FormGroup>
                </Form>
              </CardBody>
            </Card>
          </Col>

        </Row>
      </div>
    );
  }
}

export default HomePage;
