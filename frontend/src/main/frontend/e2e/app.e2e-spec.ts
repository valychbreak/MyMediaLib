import { WebstormFirstProjectPage } from './app.po';

describe('webstorm-first-project App', function() {
  let page: WebstormFirstProjectPage;

  beforeEach(() => {
    page = new WebstormFirstProjectPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
