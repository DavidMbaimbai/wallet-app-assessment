import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DebitTransactionComponent } from './debit-transaction.component';

describe('DebitTransactionComponent', () => {
  let component: DebitTransactionComponent;
  let fixture: ComponentFixture<DebitTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DebitTransactionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DebitTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
