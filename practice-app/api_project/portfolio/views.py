from django.shortcuts import get_object_or_404
from rest_framework import viewsets
from rest_framework.response import Response
from Article.models import Article
from .models import Portfolio
from .serializers import PortfolioSerializer


class PortfolioViewSet(viewsets.ModelViewSet):
    """
    Provides a get method handler.
    """
    queryset = Portfolio.objects.all()
    serializer_class = PortfolioSerializer

    def list(self, request):  # GET articles/
        serializer = ArticleSerializer(queryset)
        return Response(serializer.data)

    def create(self, request):  # POST articles/ with Article object in body
        try:
            Article.objects.create(owner=request.data["owner"], article_title=request.data["article_title"],
                                   article_text=request.data["article_text"])
            return Response(status=200)
        except:
            return Response(status=400)

    def retrieve(self, request, pk=None):  # GET articles/<id>/
        queryset = Article.objects.all()
        article = get_object_or_404(queryset, pk=pk)
        serializer = ArticleSerializer(article)
        return Response(serializer.data)

    def update(self, request, pk=None):  # PUT articles/<id>/  with Article object in body
        queryset = Article.objects.all()
        article = get_object_or_404(queryset, pk=pk)
        try:
            article = Article(owner=request.data["owner"], article_title=request.data["article_title"],
                              article_text=request.data["article_text"])
            article.save()
            return Response(status=200)
        except:
            return Response(status=400)

    def destroy(self, request, pk=None):  # DELETE articles/<id>/
        print("aa")
        queryset = Article.objects.all()
        article = get_object_or_404(queryset, pk=pk)
        try:
            article.delete()
            return Response(status=204)
        except:
            return Response(status=400)

