import React, { Component } from 'react';
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  Col,
  Row,
  Form,
  FormGroup,
  FormText,
  Input,
  Label,
} from 'reactstrap';

const axios = require('axios')
const baseUrl = "http://52.87.206.237:8000/"

class Articles extends Component {
  constructor(props) {
    super(props);

    this.state = {
      articles : [],
      title: "",
      text: "",
    };
  }

  componentDidMount = () => {
    axios.get(baseUrl+'articles/').then((response)=>
      {this.setState({articles : response.data})})
  }

  postNewArticle = (e) => {
    e.preventDefault();
    axios.post(baseUrl+'articles/',{article_title: this.state.title, article_text: this.state.text}).then((response)=>
        {window.location.reload();}
      )
  }

  handleChange = (e) => {
    this.setState({[e.target.name] : e.target.value})
  }

  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          <Col sm="12" md="12" xl="12">
            <Card>
              <CardHeader>
                <strong>Add New Article</strong>
              </CardHeader>
              <CardBody>
                <Form onSubmit={this.postNewArticle} className="form-horizontal">
                  <FormGroup row>
                    <Col md="3">
                      <Label htmlFor="hf-email">Article Title:</Label>
                    </Col>
                    <Col md="9">
                      <Input placeholder="Title" name="title" value={this.state.title} onChange={this.handleChange} />
                      <FormText className="help-block">Please enter a title</FormText>
                    </Col>
                  </FormGroup>
                  <FormGroup row>
                    <Col md="3">
                      <Label htmlFor="hf-email">Article Text:</Label>
                    </Col>
                    <Col md="9">
                      <Input type="textarea" placeholder="Write what you want" name="text" value={this.state.text} onChange={this.handleChange} />
                    </Col>
                  </FormGroup>
                  <Col md="3">
                      <Button type="submit" color="primary">Add This Article</Button>
                  </Col>
                </Form>
              </CardBody>
            </Card>
          </Col>
        </Row>

        <Row>
          {this.state.articles.map((value, index)=>{
            return(
          <Col sm="12" md="12" xl="12" key={index}>
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
