# -*- coding: utf-8 -*-
import scrapy
from scrapy.linkextractors import LinkExtractor
from scrapy.spiders import CrawlSpider, Rule
from lxml import etree
from jianshu.items import JianshuItem

class RentSpiderSpider(CrawlSpider):
    name = 'rent_spider'
    allowed_domains = ['https://bj.58.com']
    start_urls = ['https://bj.58.com/pinpaigongyu/pn/{page}/?minprice=2000_4000']

    # 可以指定爬虫抓取的规则, 支持正则表达式
    # https://www.jianshu.com/p/9220a3a4e8f4
    # https://www.jianshu.com/p/1377e37aaaae
    rules = (
        Rule(LinkExtractor(allow=r'https://www.jianshu.com/p/[0-9a-z]{12}.*'), callback='parse_item', follow=True),
    )

    def parse_item(self, response):
        html = etree.HTML(response.text)
        item = RentItem()
        item['title'] = html.xpath("//title/text()")[0].split('-')[0]
        item['name'] = html.xpath('*//span[@class="_22gUMi"]/text()')[0]
        item['url'] = response.url.split('?')[0]
        collection = html.xpath('*//div[@class="_2Nttfz"]/a/span[@class="_2-Djqu"]/text()')[0]
        if collection:
             item['collection'] = ','.join(collection)
        yield item





        # item = {}
        # #item['domain_id'] = response.xpath('//input[@id="sid"]/@value').get()
        # #item['name'] = response.xpath('//div[@id="name"]').get()
        # #item['description'] = response.xpath('//div[@id="description"]').get()
        # return item
