from django.test import TestCase
from rest_framework.test import APIRequestFactory
from Article.models import Article
from Article.views import ArticleViewSet

class ArticleTestCase(TestCase):
	def setUp(self):
		Article.objects.create(article_title="test title 1", article_text="test text 1")
		Article.objects.create(article_title="test title 2", article_text="test text 2")

	def test_articles(self):
		article1 = Article.objects.get(article_title="test title 1")
		article2 = Article.objects.get(article_title="test title 2")

		#create necessary views for sending requests
		getRequest = ArticleViewSet.as_view(actions={'get': 'retrieve'})
		postRequest = ArticleViewSet.as_view(actions={'post': 'create'})
		updateRequest = ArticleViewSet.as_view(actions={'put': 'update'})
		deleteRequest = ArticleViewSet.as_view(actions={'delete': 'destroy'})

		factory = APIRequestFactory() # to create requests

		#test get
		self.assertEqual(getRequest(factory.get('articles/'), pk=article1.id).status_code, 200)
		self.assertEqual(getRequest(factory.get('articles/'), pk=article2.id).status_code, 200)
		#test post
		self.assertEqual(postRequest(factory.post('articles/', {"article_title":"test title 3", "article_text":"test text 3"})).status_code, 200)
		article3 = Article.objects.get(article_title="test title 3")
		self.assertEqual(getRequest(factory.get('articles/'), pk=article3.id).data["article_text"], "test text 3")
		#test delete
		self.assertEqual(deleteRequest(factory.delete('articles/'), pk=article2.id).status_code, 204)
		self.assertEqual(getRequest(factory.get('articles/'), pk=article2.id).status_code, 404) # should have been deleted
		#test update
		self.assertEqual(updateRequest(factory.put('articles/', {"article_title": "test title 1 updated", "article_text":"test text 1 updated"}), pk=article1.id).status_code, 200)
		self.assertEqual(getRequest(factory.get('articles/'),pk=1).data["article_text"], "test text 1 updated")
		